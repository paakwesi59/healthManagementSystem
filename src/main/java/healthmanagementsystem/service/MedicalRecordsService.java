package healthmanagementsystem.service;

import healthmanagementsystem.dto.MedicalRecordsRequest;
import healthmanagementsystem.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsService {
    MedicalRecords createMedicalRecord(MedicalRecordsRequest request);
    MedicalRecords updateMedicalRecord(String id, MedicalRecordsRequest request);
    MedicalRecords getMedicalRecordById(String id);
    List<MedicalRecords> getMedicalRecordsByPatientId(String patientId);
    List<MedicalRecords> getMedicalRecordsByDoctorId(String doctorId);
    void deleteMedicalRecord(String id);
}
