package healthmanagementsystem.service;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.PatientDetails;
import healthmanagementsystem.dto.PatientResponseMessage;
import healthmanagementsystem.model.Patient;

public interface PatientService {
    PatientResponseMessage registerPatient(PatientDto patientDto);
    PatientResponseMessage findPatientById(String id); // Change this to PatientResponseMessage
    Patient getPatientById(String patientId);
    PatientResponseMessage findPatientByRegistrationNumber(String registrationNumber);

}
