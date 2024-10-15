package healthmanagementsystem.service;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.ResponseMessage;

public interface PatientService {
    ResponseMessage registerPatient(PatientDto patientDto);
    ResponseMessage findPatientById(String id);
}
