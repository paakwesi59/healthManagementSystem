package healthmanagementsystem.service;

import healthmanagementsystem.dto.*;
import healthmanagementsystem.exception.UserInvitationException;
import healthmanagementsystem.model.Role;
import healthmanagementsystem.model.User;
import healthmanagementsystem.repository.UserRepository;
import healthmanagementsystem.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Pattern;

import static healthmanagementsystem.util.StringConstant.*;

@RequiredArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    // Regular expression for password validation
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    private final SecureRandom random = new SecureRandom();

    // Generate a random temporary password that meets the requirements
    private String generateTemporaryPassword() {
        StringBuilder password = new StringBuilder();

        // Add one character from each required category
        password.append(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));
        password.append(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));
        password.append(DIGIT_CHARS.charAt(random.nextInt(DIGIT_CHARS.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Fill the rest of the password length with random characters from all categories
        String allChars = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGIT_CHARS + SPECIAL_CHARS;
        for (int i = REQUIRED_CHAR_COUNT; i < TEMP_PASSWORD_LENGTH; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password to avoid predictable patterns
        return shuffleString(password.toString());
    }

    // Shuffle the characters in a string to randomize their order
    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    // Validate password against pattern
    private void validatePassword(String password) {
        if (!pattern.matcher(password).matches()) {
            throw new UserInvitationException(PASSWORD_NOT_SECURE);
        }
    }

    // Generate staff number based on role
    private String generateStaffNumber(Role role) {
        String prefix = role.name().substring(0, 3).toUpperCase(); // First three letters of the role
        int count = userRepository.countByRole(role); // Count of users with the same role

        // Generate the staff number in the format "PREFIX0001"
        return String.format("%s%04d", prefix, count + 1);
    }

    @Override
    public ResponseMessage inviteUser(InviteUserRequest inviteUserRequest) {
        if (findUserByEmail(inviteUserRequest.getEmail()) != null) {
            throw new UserInvitationException(USER_WITH_EMAIL + inviteUserRequest.getEmail() + EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(inviteUserRequest.getFirstName());
        user.setLastName(inviteUserRequest.getLastName());
        user.setEmail(inviteUserRequest.getEmail());
        user.setPhoneNumber(inviteUserRequest.getPhoneNumber());

        // Convert role input to uppercase for case-insensitive matching
        try {
            Role role = Role.valueOf(inviteUserRequest.getRole().toUpperCase());
            user.setRole(role);
            user.setStaffNumber(generateStaffNumber(role)); // Set the staff number based on the role
        } catch (IllegalArgumentException e) {
            throw new UserInvitationException(INVALID_ROLE + inviteUserRequest.getRole());
        }

        user.setEnabled(true);
        String tempPassword = generateTemporaryPassword(); // Generate the temporary password
        validatePassword(tempPassword); // Validate the generated password
        user.setPassword(passwordEncoder.encode(tempPassword)); // Store the encoded temporary password
        user.setTemporaryPassword(true);

        userRepository.save(user);

        // Call sendInvitationEmail with the correct parameters
        emailService.sendInvitationEmail(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString(),
                user.getStaffNumber(), // Pass the staff number correctly
                tempPassword // Pass the temporary password correctly
        );

        return new ResponseMessage(SUCCESS, INVITATION_SENT + inviteUserRequest.getEmail());
    }

    @Override
    public void changeUserPassword(String token, ChangePasswordRequest changePasswordRequest) {
        validatePassword(changePasswordRequest.getNewPassword()); // Validate the new password

        // Extract user ID from the token
        String userId = jwtTokenUtil.getUserIdFromToken(token.replace("Bearer ", ""));

        // Retrieve user by ID
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserInvitationException(USER_NOT_FOUND_WITH_ID + userId)
        );

        // Check if the current password is correct
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new UserInvitationException(INVALID_CREDENTIALS);
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setTemporaryPassword(false); // Set this to false after password change
        userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = findUserByStaffNumber(loginRequest.getStaffNumber()); // Use staff number instead of email

        if (user == null) {
            throw new UserInvitationException(USER_NOT_FOUND + loginRequest.getStaffNumber());
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserInvitationException(INVALID_CREDENTIALS);
        }

        // If the user is logging in for the first time, generate a password change token
        if (user.isTemporaryPassword()) {
            String tempAccessToken = jwtTokenUtil.generateAccessToken(user.getId(), user.getRole().toString());
            return new LoginResponse(SUCCESS, FIRST_TIME_LOGIN_PASSWORD_CHANGE_REQUIRED, tempAccessToken, null);
        }

        // Generate access and refresh tokens for normal logins
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId(), user.getRole().toString());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        // Set the tokens in the response
        return new LoginResponse(SUCCESS, LOGIN_SUCCESSFUL, accessToken, refreshToken);
    }

    // Helper method to find user by email
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findUserByStaffNumber(String staffNumber) {
        return userRepository.findByStaffNumber(staffNumber).orElse(null);
    }

    @Override
    public boolean isTemporaryPassword(String email) {
        User user = findUserByEmail(email);
        return user != null && user.isTemporaryPassword();
    }

    @Override
    public User getUserById(String userId) {  // Add this method
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserInvitationException(USER_NOT_FOUND_WITH_ID + userId));
    }
}



