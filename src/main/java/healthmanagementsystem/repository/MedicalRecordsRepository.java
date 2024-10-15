package healthmanagementsystem.repository;

import healthmanagementsystem.model.MedicalRecords;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, String> {
    List<MedicalRecords> findByPatient(Patient patient);
    List<MedicalRecords> findByDoctor(User doctor);
}
