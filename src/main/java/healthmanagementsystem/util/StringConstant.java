package healthmanagementsystem.util;

public class StringConstant {
    public static final String INVITATION_SENT = "Invitation sent to: ";
    public static final String EMAIL_ALREADY_EXISTS = " already exists.";
    public static final String USER_WITH_EMAIL = "User with email: ";
    public static final String INVALID_ROLE = "Invalid role specified: ";
    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGIT_CHARS = "0123456789";
    public static final String SPECIAL_CHARS = "@$!%*?&";
    public static final int TEMP_PASSWORD_LENGTH = 8; // Length of the temporary password
    public static final int REQUIRED_CHAR_COUNT = 4; // Number of required characters to include
    public static final String USER_NOT_FOUND = "User not found with email: ";
    public static final String USER_NOT_FOUND_WITH_ID = "User not found with ID: ";
    public static final String INVALID_CREDENTIALS = "Invalid credentials.";
    public static final String LOGIN_SUCCESSFUL = "Login successful.";
    public static final String PASSWORD_CHANGE_SUCCESSFUL = "Password changed successfully.";
    public static final String PASSWORD_NOT_SECURE = "Password not secure.";
    public static final String FIRST_TIME_LOGIN_PASSWORD_CHANGE_REQUIRED = "First time login password change required.";
    public static final String PATIENT_REGISTERED_SUCCESSFULLY = "Patient registered successfully";
    public static final String FAILED_TO_REGISTER_PATIENT = "Failed to register patient: ";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
    public static final String PATIENT_FOUND = "Patient found";

}
