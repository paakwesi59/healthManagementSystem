package healthmanagementsystem.controller;

import healthmanagementsystem.dto.ChangePasswordRequest;
import healthmanagementsystem.dto.LoginRequest;
import healthmanagementsystem.dto.LoginResponse;
import healthmanagementsystem.dto.ResponseMessage;
import healthmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static healthmanagementsystem.util.StringConstant.PASSWORD_CHANGE_SUCCESSFUL;
import static healthmanagementsystem.util.StringConstant.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changeUserPassword(token, changePasswordRequest);
        return new ResponseEntity<>(new ResponseMessage(SUCCESS, PASSWORD_CHANGE_SUCCESSFUL), HttpStatus.OK);
    }
}


