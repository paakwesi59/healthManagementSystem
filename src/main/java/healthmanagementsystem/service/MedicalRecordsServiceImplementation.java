package healthmanagementsystem.service;


import healthmanagementsystem.dto.MedicalRecordsRequest;
import healthmanagementsystem.exception.ResourceNotFoundException;
import healthmanagementsystem.model.MedicalRecords;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import healthmanagementsystem.repository.MedicalRecordsRepository;
import healthmanagementsystem.repository.PatientRepository;
import healthmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MedicalRecordsServiceImplementation implements MedicalRecordsService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Override
    public MedicalRecords createMedicalRecord(MedicalRecordsRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + request.getPatientId()));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + request.getDoctorId()));

        MedicalRecords medicalRecord = new MedicalRecords();
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatmentNote(request.getTreatmentNote());
        medicalRecord.setDateOfVisit(request.getDateOfVisit() != null ? request.getDateOfVisit() : LocalDate.now());
        medicalRecord.setFollowUpRequired(request.isFollowUpRequired());
        medicalRecord.setAllergies(request.getAllergies());
        medicalRecord.setLabReport(request.getLabReport());
        medicalRecord.setPrescribedMedication(request.getPrescribedMedication());
        medicalRecord.setFollowUpDate(request.getFollowUpDate());

        return medicalRecordsRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecords updateMedicalRecord(String id, MedicalRecordsRequest request) {
        MedicalRecords medicalRecord = medicalRecordsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with ID: " + id));

        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatmentNote(request.getTreatmentNote());
        medicalRecord.setFollowUpRequired(request.isFollowUpRequired());
        medicalRecord.setAllergies(request.getAllergies());
        medicalRecord.setLabReport(request.getLabReport());
        medicalRecord.setPrescribedMedication(request.getPrescribedMedication());
        medicalRecord.setFollowUpDate(request.getFollowUpDate());

        return medicalRecordsRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecords getMedicalRecordById(String id) {
        return medicalRecordsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with ID: " + id));
    }

    @Override
    public List<MedicalRecords> getMedicalRecordsByPatientId(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + patientId));
        return medicalRecordsRepository.findByPatient(patient);
    }

    @Override
    public List<MedicalRecords> getMedicalRecordsByDoctorId(String doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId));
        return medicalRecordsRepository.findByDoctor(doctor);
    }

    @Override
    public void deleteMedicalRecord(String id) {
        MedicalRecords medicalRecord = medicalRecordsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with ID: " + id));
        medicalRecordsRepository.delete(medicalRecord);
    }
}
