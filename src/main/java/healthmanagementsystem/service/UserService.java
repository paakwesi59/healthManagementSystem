package healthmanagementsystem.service;

import healthmanagementsystem.dto.ChangePasswordRequest;
import healthmanagementsystem.dto.InviteUserRequest;
import healthmanagementsystem.dto.LoginRequest;
import healthmanagementsystem.dto.LoginResponse;
import healthmanagementsystem.dto.ResponseMessage;
import healthmanagementsystem.model.User;

public interface UserService {
    ResponseMessage inviteUser(InviteUserRequest inviteUserRequest);

    User findUserByEmail(String email);

    User findUserByStaffNumber(String staffNumber); // New method for finding user by staff number

    void changeUserPassword(String token, ChangePasswordRequest changePasswordRequest);

    LoginResponse loginUser(LoginRequest loginRequest);

    boolean isTemporaryPassword(String email);
}


