
//package healthmanagementsystem.service;
//
//import healthmanagementsystem.dto.PatientDto;
//import healthmanagementsystem.dto.ResponseMessage;
//import healthmanagementsystem.dto.PatientDetails;
//import healthmanagementsystem.model.Patient;
//import healthmanagementsystem.repository.PatientRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.Optional;
//import java.util.UUID;
//
//import static healthmanagementsystem.util.StringConstant.*;
//
//@RequiredArgsConstructor
//@Service
//public class PatientServiceImplementation implements PatientService {
//
//    private final PatientRepository patientRepository;
//
//    @Override
//    public ResponseMessage registerPatient(PatientDto patientDto) {
//        try {
//            Patient patient = new Patient();
//            patient.setId(UUID.randomUUID().toString());
//            patient.setRegistrationNumber(generateRegistrationNumber());
//            patient.setRegistrationDate(LocalDate.now());
//            patient.setFirstName(patientDto.getFirstName());
//            patient.setLastName(patientDto.getLastName());
//            patient.setOtherNames(patientDto.getOtherNames());
//            patient.setPhoneNumber(patientDto.getPhoneNumber());
//            patient.setGender(patientDto.getGender());
//            patient.setDateOfBirth(patientDto.getDateOfBirth());
//            patient.setAge(calculateAge(patientDto.getDateOfBirth()));
//            patient.setMaritalStatus(patientDto.getMaritalStatus());
//            patient.setOccupation(patientDto.getOccupation());
//            patient.setReligion(patientDto.getReligion());
//            patient.setNationality(patientDto.getNationality());
//            patient.setAddress(patientDto.getAddress());
//            patient.setCity(patientDto.getCity());
//            patient.setTown(patientDto.getTown());
//            patient.setFatherName(patientDto.getFatherName());
//            patient.setFatherPhoneNumber(patientDto.getFatherPhoneNumber());
//            patient.setMotherName(patientDto.getMotherName());
//            patient.setMotherPhoneNumber(patientDto.getMotherPhoneNumber());
//            patient.setGuardianName(patientDto.getGuardianName());
//            patient.setGuardianPhoneNumber(patientDto.getGuardianPhoneNumber());
//            patient.setDeceased(patientDto.isDeceased());
//            patient.setAdmitted(patientDto.isAdmitted());
//
//            patientRepository.save(patient);
//
//            // Create PatientDetails to return
//            PatientDetails patientDetails = new PatientDetails(
//                    patient.getRegistrationNumber(),
//                    patient.getFirstName() + " " + patient.getOtherNames() + " " + patient.getLastName(), // Full name
//                    patient.getGender(),
//                    patient.getAge()
//            );
//
//            return new ResponseMessage(SUCCESS, PATIENT_REGISTERED_SUCCESSFULLY, patientDetails);
//        } catch (Exception e) {
//            return new ResponseMessage(ERROR, FAILED_TO_REGISTER_PATIENT + e.getMessage(), null);
//        }
//    }
//
//    @Override
//    public ResponseMessage findPatientById(String id) {
//        Optional<Patient> patientOptional = patientRepository.findById(id);
//        if (patientOptional.isPresent()) {
//            Patient patient = patientOptional.get();
//            PatientDetails patientDetails = new PatientDetails(
//                    patient.getRegistrationNumber(),
//                    patient.getFirstName() + " " + patient.getOtherNames() + " " + patient.getLastName(),
//                    patient.getGender(),
//                    patient.getAge()
//            );
//
//            return new ResponseMessage(SUCCESS, PATIENT_FOUND, patientDetails);
//        } else {
//            return new ResponseMessage(ERROR, PATIENT_NOT_FOUND, null);
//        }
//    }
//
//    @Override
//    public Patient getPatientById(String patientId) {  // Add this method
//        return patientRepository.findById(patientId)
//                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
//    }
//
//    private String generateRegistrationNumber() {
//        return "PAT-" + UUID.randomUUID().toString().substring(0, 8);
//    }
//
//    private int calculateAge(LocalDate dateOfBirth) {
//        return Period.between(dateOfBirth, LocalDate.now()).getYears();
//    }
//}

package healthmanagementsystem.service;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.PatientDetails;
import healthmanagementsystem.dto.PatientResponseMessage;
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
    public PatientResponseMessage registerPatient(PatientDto patientDto) {
        try {
            Patient patient = new Patient();
            patient.setId(UUID.randomUUID().toString());
            patient.setRegistrationNumber(generateUniqueRegistrationNumber());  // Use updated method
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

            return new PatientResponseMessage(SUCCESS, "Patient registered successfully",
                    new PatientDetails(
                            patient.getRegistrationNumber(),
                            patient.getFirstName() + " " + patient.getOtherNames() + " " + patient.getLastName(),
                            patient.getGender(),
                            patient.getAge()
                    )
            );
        } catch (Exception e) {
            return new PatientResponseMessage(ERROR, "Failed to register patient: " + e.getMessage(), null);
        }
    }

    @Override
    public PatientResponseMessage findPatientById(String id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            return new PatientResponseMessage(SUCCESS, "Patient found",
                    new PatientDetails(
                            patient.getRegistrationNumber(),
                            patient.getFirstName() + " " + patient.getOtherNames() + " " + patient.getLastName(),
                            patient.getGender(),
                            patient.getAge()
                    )
            );
        } else {
            return new PatientResponseMessage(ERROR, "Patient not found", null);
        }
    }

    @Override
    public Patient getPatientById(String patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
    }

    // New method to find patient by registration number
    @Override
    public PatientResponseMessage findPatientByRegistrationNumber(String registrationNumber) {
        Optional<Patient> patientOptional = patientRepository.findByRegistrationNumber(registrationNumber);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            return new PatientResponseMessage(SUCCESS, "Patient found",
                    new PatientDetails(
                            patient.getRegistrationNumber(),
                            patient.getFirstName() + " " + patient.getOtherNames() + " " + patient.getLastName(),
                            patient.getGender(),
                            patient.getAge()
                    )
            );
        } else {
            return new PatientResponseMessage(ERROR, "Patient not found", null);
        }
    }

    // Updated method to ensure unique registration number
    private synchronized String generateUniqueRegistrationNumber() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        long count = patientRepository.countByRegistrationYear(currentYear);

        // Ensure uniqueness by checking if the generated registration number already exists
        String registrationNumber;
        do {
            registrationNumber = String.format("PAT%d-%d", currentYear, count + 1);
            count++;  // Increment to try another number if already exists
        } while (patientRepository.existsByRegistrationNumber(registrationNumber));

        return registrationNumber;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}

