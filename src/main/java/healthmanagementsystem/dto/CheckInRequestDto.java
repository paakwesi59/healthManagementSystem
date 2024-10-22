package healthmanagementsystem.dto;

import lombok.Data;

@Data
public class CheckInRequestDto {
    private String registrationNumber;  // Patient's registration number
    private String staffId;              // Staff member ID checking in the patient
}
