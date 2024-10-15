package healthmanagementsystem.controller;

import healthmanagementsystem.dto.InviteUserRequest;
import healthmanagementsystem.dto.ResponseMessage;
import healthmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/invite")
    public ResponseEntity<ResponseMessage> inviteUser(@RequestBody InviteUserRequest inviteUserRequest) {
        return new ResponseEntity<>(userService.inviteUser(inviteUserRequest), HttpStatus.CREATED);
    }
}
