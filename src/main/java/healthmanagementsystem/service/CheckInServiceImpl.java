//package healthmanagementsystem.service;
//
//import healthmanagementsystem.model.CheckIn;
//import healthmanagementsystem.model.CheckInStatus;
//import healthmanagementsystem.model.Patient;
//import healthmanagementsystem.model.User;
//import healthmanagementsystem.repository.CheckInRepository;
//import healthmanagementsystem.repository.PatientRepository;
//import healthmanagementsystem.repository.UserRepository; // Make sure to import UserRepository
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class CheckInServiceImpl implements CheckInService {
//    private final CheckInRepository checkInRepository;
//    private final PatientRepository patientRepository;
//    private final UserRepository userRepository; // Inject UserRepository
//
//    @Override
//    public CheckIn checkInPatient(String registrationNumber, String staffId) {
//        Patient patient = patientRepository.findByRegistrationNumber(registrationNumber)
//                .orElseThrow(() -> new RuntimeException("Patient not found with registration number: " + registrationNumber));
//        User staffMember = userRepository.findById(staffId)
//                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
//
//        CheckIn checkIn = new CheckIn();
//        checkIn.setPatient(patient);
//        checkIn.setCheckedInBy(staffMember);
//        checkIn.setCheckInTime(LocalDateTime.now());
//        checkIn.setStatus(CheckInStatus.WAITING); // Set initial status
//        return checkInRepository.save(checkIn);
//    }
//
//    @Override
//    public CheckIn checkOutPatient(Long checkInId, String staffId) {
//        User staffMember = userRepository.findById(staffId)
//                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
//
//        CheckIn checkIn = checkInRepository.findById(checkInId)
//                .orElseThrow(() -> new RuntimeException("Check-in not found with id: " + checkInId));
//
//        checkIn.setCheckedInBy(staffMember);
//        checkIn.setCheckOutTime(LocalDateTime.now());
//        checkIn.setStatus(CheckInStatus.DISCHARGED); // Update status to discharged
//        return checkInRepository.save(checkIn);
//    }
//
//    @Override
//    public List<CheckIn> getActiveCheckIns() {
//        return checkInRepository.findByStatus(CheckInStatus.WAITING);
//    }
//
//    @Override
//    public List<CheckIn> getCheckInsByStaffMember(String staffId) {
//        User staffMember = userRepository.findById(staffId)
//                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
//        return checkInRepository.findByCheckedInBy(staffMember);
//    }
//}

package healthmanagementsystem.service;

import healthmanagementsystem.dto.CheckInResponseDto;
import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.CheckInStatus;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import healthmanagementsystem.repository.CheckInRepository;
import healthmanagementsystem.repository.PatientRepository;
import healthmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CheckInServiceImpl implements CheckInService {
    private final CheckInRepository checkInRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Override
    public CheckInResponseDto checkInPatient(String registrationNumber, String staffId) {
        Patient patient = patientRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new RuntimeException("Patient not found with registration number: " + registrationNumber));
        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        CheckIn checkIn = new CheckIn();
        checkIn.setPatient(patient);
        checkIn.setCheckedInBy(staffMember);
        checkIn.setCheckInTime(LocalDateTime.now());
        checkIn.setStatus(CheckInStatus.WAITING); // Set initial status
        checkIn = checkInRepository.save(checkIn); // Save CheckIn to get generated ID

        // Map CheckIn and Patient data to CheckInResponseDto
        CheckInResponseDto response = new CheckInResponseDto();
        response.setRegistrationNumber(patient.getRegistrationNumber());
        response.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        response.setAge(patient.getAge());
        response.setStatus(checkIn.getStatus());

        return response; // Return the response DTO
    }

    @Override
    public CheckIn checkOutPatient(Long checkInId, String staffId) {
        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        CheckIn checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new RuntimeException("Check-in not found with id: " + checkInId));

        checkIn.setCheckedInBy(staffMember);
        checkIn.setCheckOutTime(LocalDateTime.now());
        checkIn.setStatus(CheckInStatus.DISCHARGED); // Update status to discharged
        return checkInRepository.save(checkIn);
    }

    @Override
    public List<CheckInResponseDto> getActiveCheckIns() {
        // Fetch active check-ins and map them to CheckInResponseDto
        return checkInRepository.findByStatus(CheckInStatus.WAITING).stream()
                .map(checkIn -> {
                    Patient patient = checkIn.getPatient(); // Get associated patient
                    CheckInResponseDto response = new CheckInResponseDto();
                    response.setRegistrationNumber(patient.getRegistrationNumber());
                    response.setPatientName(patient.getFirstName() + " " + patient.getLastName());
                    response.setAge(patient.getAge());
                    response.setStatus(checkIn.getStatus());
                    return response; // Return the mapped response DTO
                })
                .collect(Collectors.toList()); // Collect results into a list
    }

    @Override
    public List<CheckIn> getCheckInsByStaffMember(String staffId) {
        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return checkInRepository.findByCheckedInBy(staffMember);
    }
}
