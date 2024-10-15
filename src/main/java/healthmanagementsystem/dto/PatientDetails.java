package healthmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetails {
    private String registrationNumber;
    private String fullName;
    private String gender;
    private int age;
}
