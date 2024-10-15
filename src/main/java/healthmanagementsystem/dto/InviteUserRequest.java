package healthmanagementsystem.dto;

import lombok.Data;

@Data
public class InviteUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String role;
    private String staffNumber; // New field for staff number
}

