package healthmanagementsystem.dto;

import healthmanagementsystem.model.CheckInStatus;
import lombok.Data;

@Data
public class CheckInResponseDto {
    private String patientName;          // Full name of the patient
    private int age;                     // Age of the patient
    private CheckInStatus status;        // Check-in status
    private String registrationNumber;   // Patient's registration number
}
