package healthmanagementsystem.repository;

import healthmanagementsystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    long countByRegistrationYear(int year);
    boolean existsByRegistrationNumber(String registrationNumber);
    Optional<Patient> findByRegistrationNumber(String registrationNumber);
}
