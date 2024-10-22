package healthmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseMessage {
    private String status; // SUCCESS or ERROR
    private String message; // Message to return
    private Object data; // Could be PatientDetails or any other object
}
