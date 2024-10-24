package healthmanagementsystem.dto;

import healthmanagementsystem.model.CheckInStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CheckInResponseDto {
    private String patientName;          // Full name of the patient
    private int age;                     // Age of the patient
    private CheckInStatus status;        // Check-in status
    private String registrationNumber;   // Patient's registration number
    private LocalDateTime checkInTime;   // Check-in time
    private Integer queuePosition;
}
