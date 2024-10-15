package healthmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordsRequest {
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatmentNote;
    private LocalDate dateOfVisit;
    private boolean followUpRequired;
    private String allergies;
    private String labReport;
    private String prescribedMedication;
    private LocalDate followUpDate;
}
