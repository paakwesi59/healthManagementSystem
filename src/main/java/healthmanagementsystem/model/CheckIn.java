package healthmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)  // Reference to the Patient
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "checked_in_by", nullable = false)  // Reference to the User (staff member)
    private User checkedInBy;

    private LocalDateTime checkInTime;  // Time the patient checked in
    private LocalDateTime checkOutTime;  // Time the patient checked out

    @Enumerated(EnumType.STRING)
    private CheckInStatus status;  // Status of the patient (WAITING, IN_PROGRESS, DISCHARGED)

    private Integer priorityLevel;  // Optional: For handling urgent or prioritized patients
}
