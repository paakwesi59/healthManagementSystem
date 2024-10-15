package healthmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;

    // Add any other fields as necessary
}
