package healthmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Patient {
    @Id
    private String id;
    @OneToMany(mappedBy = "patient")
    private List<MedicalRecords> medicalRecords;

    @OneToMany(mappedBy = "patient")  // Relationship with CheckIn
    private List<CheckIn> checkIns;

    @Column(unique=true)
    private String registrationNumber;
    private LocalDate registrationDate;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String phoneNumber;
    private String email;
    private String gender;
    private LocalDate dateOfBirth;
    private int age;
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
    private String motherPhoneNumber;
    private String guardianName;
    private String guardianPhoneNumber;
    private boolean deceased;
    private boolean admitted;
}
