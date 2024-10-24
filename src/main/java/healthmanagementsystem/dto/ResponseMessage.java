package healthmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String status;
    private String message;
    private PatientDetails patientDetails;
    private String staffNumber; // New field for staff number

    // Overloaded constructor for status and message
    public ResponseMessage(String status, String message) {
        this.status = status;
        this.message = message;
        this.staffNumber = null;
    }

}

