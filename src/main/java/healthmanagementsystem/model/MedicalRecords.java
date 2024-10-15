package healthmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@Entity
public class MedicalRecords {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    private String diagnosis;
    private String treatmentNote;
    private LocalDate dateOfVisit;
    private boolean followUpRequired;
    private String allergies;
    private String labReport;
    private String prescribedMedication;
    private LocalDate followUpDate;
}
