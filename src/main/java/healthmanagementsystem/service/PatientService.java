package healthmanagementsystem.service;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.ResponseMessage;
import healthmanagementsystem.model.Patient;

public interface PatientService {
    ResponseMessage registerPatient(PatientDto patientDto);
    ResponseMessage findPatientById(String id);
    Patient getPatientById(String patientId);
}
