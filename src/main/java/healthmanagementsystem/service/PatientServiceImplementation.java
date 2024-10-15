package healthmanagementsystem.service;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.ResponseMessage;
import healthmanagementsystem.dto.PatientDetails;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;
import static healthmanagementsystem.util.StringConstant.*;

@RequiredArgsConstructor
@Service
public class PatientServiceImplementation implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public ResponseMessage registerPatient(PatientDto patientDto) {
        try {
            Patient patient = new Patient();
            patient.setId(UUID.randomUUID().toString());
            patient.setRegistrationNumber(generateRegistrationNumber());
            patient.setRegistrationDate(LocalDate.now());
            patient.setFirstName(patientDto.getFirstName());
            patient.setLastName(patientDto.getLastName());
            patient.setOtherNames(patientDto.getOtherNames());
            patient.setPhoneNumber(patientDto.getPhoneNumber());
            patient.setGender(patientDto.getGender());
            patient.setDateOfBirth(patientDto.getDateOfBirth());
            patient.setAge(calculateAge(patientDto.getDateOfBirth()));
            patient.setMaritalStatus(patientDto.getMaritalStatus());
            patient.setOccupation(patientDto.getOccupation());
            patient.setReligion(patientDto.getReligion());
            patient.setNationality(patientDto.getNationality());
            patient.setAddress(patientDto.getAddress());
            patient.setCity(patientDto.getCity());
            patient.setTown(patientDto.getTown());
            patient.setFatherName(patientDto.getFatherName());
            patient.setFatherPhoneNumber(patientDto.getFatherPhoneNumber());
            patient.setMotherName(patientDto.getMotherName());
            patient.setMotherPhoneNumber(patientDto.getMotherPhoneNumber());
            patient.setGuardianName(patientDto.getGuardianName());
            patient.setGuardianPhoneNumber(patientDto.getGuardianPhoneNumber());
            patient.setDeceased(patientDto.isDeceased());
            patient.setAdmitted(patientDto.isAdmitted());

            patientRepository.save(patient);

            // Create PatientDetails to return
            PatientDetails patientDetails = new PatientDetails(
                    patient.getRegistrationNumber(),
                    patient.getFirstName() + " " + " " + patient.getOtherNames() +" " + patient.getLastName(), // Full name
                    patient.getGender(),
                    patient.getAge()
            );

            return new ResponseMessage(SUCCESS, PATIENT_REGISTERED_SUCCESSFULLY, patientDetails);
        } catch (Exception e) {
            return new ResponseMessage(ERROR, FAILED_TO_REGISTER_PATIENT + e.getMessage(), null);
        }
    }

    @Override
    public ResponseMessage findPatientById(String id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            PatientDetails patientDetails = new PatientDetails(
                    patient.getRegistrationNumber(),
                    patient.getFirstName() + " " + " " + patient.getOtherNames() + " " + patient.getLastName(),
                    patient.getGender(),
                    patient.getAge()
            );

            return new ResponseMessage(SUCCESS, PATIENT_FOUND, patientDetails);
        } else {
            return new ResponseMessage(ERROR, PATIENT_NOT_FOUND, null);
        }
    }

    private String generateRegistrationNumber() {
        return "PAT-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}

