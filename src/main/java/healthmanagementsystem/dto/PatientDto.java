package healthmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

@Data
public class PatientDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String otherNames;

    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    private String gender;

    @NotNull
    private LocalDate dateOfBirth;

    private String maritalStatus;

    private String occupation;

    private String religion;

    private String nationality;

    private String address;

    private String city;

    private String town;

    private String fatherName;

    private String fatherPhoneNumber;

    private String motherName;

    private String  motherPhoneNumber;

    private String guardianName;

    private String guardianPhoneNumber;

    private String nextOfKinName;

    private String nextOfKinPhoneNumber;

    private boolean deceased;

    private boolean admitted;
}
