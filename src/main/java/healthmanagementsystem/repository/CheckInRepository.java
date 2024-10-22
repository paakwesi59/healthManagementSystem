package healthmanagementsystem.repository;

import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.CheckInStatus;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    // Find the latest check-in for a specific patient
    Optional<CheckIn> findFirstByPatientOrderByCheckInTimeDesc(Patient patient);

    // Find all check-ins that are still active (i.e., not checked out)
    List<CheckIn> findByStatusAndCheckOutTimeIsNull(CheckInStatus status);

    // Find all check-ins handled by a specific staff member
    List<CheckIn> findByCheckedInBy(User staffMember);
}
