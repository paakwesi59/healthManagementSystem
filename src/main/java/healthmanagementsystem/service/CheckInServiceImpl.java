package healthmanagementsystem.service;

import healthmanagementsystem.config.JwtTokenUtil;
import healthmanagementsystem.dto.CheckInResponseDto;
import healthmanagementsystem.exception.PatientAlreadyCheckedInException;
import healthmanagementsystem.exception.ResourceNotFoundException;
import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.CheckInStatus;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import healthmanagementsystem.repository.CheckInRepository;
import healthmanagementsystem.repository.PatientRepository;
import healthmanagementsystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CheckInServiceImpl implements CheckInService {
    private final CheckInRepository checkInRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil; // JWT utility to extract the user ID

    @Override
    public CheckInResponseDto checkInPatient(String registrationNumber, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Assuming "Bearer " prefix
        String staffId = jwtTokenUtil.getUserIdFromToken(token); // Get the user ID from the token

        Patient patient = patientRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with registration number: " + registrationNumber));

        // Check if the patient is already checked in
        if (checkInRepository.findByPatient(patient).isPresent()) {
            throw new PatientAlreadyCheckedInException("Patient is already checked in.");
        }

        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));

        CheckIn checkIn = new CheckIn();
        checkIn.setPatient(patient);
        checkIn.setCheckedInBy(staffMember);
        checkIn.setCheckInTime(LocalDateTime.now());
        checkIn.setStatus(CheckInStatus.WAITING);

        // Assign queue position
        List<CheckIn> activeCheckIns = checkInRepository.findByStatus(CheckInStatus.WAITING);
        int queuePosition = activeCheckIns.size() + 1;
        checkIn.setQueuePosition(queuePosition);

        checkIn = checkInRepository.save(checkIn);

        return getCheckInResponseDto(patient, checkIn);
    }

    private CheckInResponseDto getCheckInResponseDto(Patient patient, CheckIn checkIn) {
        CheckInResponseDto response = new CheckInResponseDto();
        response.setRegistrationNumber(patient.getRegistrationNumber());
        response.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        response.setAge(patient.getAge());
        response.setStatus(checkIn.getStatus());
        response.setCheckInTime(checkIn.getCheckInTime()); // Set the check-in time in the response
        response.setQueuePosition(checkIn.getQueuePosition()); // Set the queue position in the response

        return response; // Return the response DTO
    }

    @Override
    public CheckIn checkOutPatient(Long checkInId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Assuming "Bearer " prefix
        String staffId = jwtTokenUtil.getUserIdFromToken(token); // Get the user ID from the token

        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));

        CheckIn checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new ResourceNotFoundException("Check-in not found with id: " + checkInId));

        checkIn.setCheckedInBy(staffMember);
        checkIn.setCheckOutTime(LocalDateTime.now());  // Record the check-out time
        checkIn.setStatus(CheckInStatus.DISCHARGED); // Update status to discharged
        return checkInRepository.save(checkIn);
    }

    @Override
    public List<CheckInResponseDto> getActiveCheckIns() {
        // Fetch active check-ins, filter out any with null queue positions, and sort by queue position
        return checkInRepository.findByStatus(CheckInStatus.WAITING).stream()
                .filter(checkIn -> checkIn.getQueuePosition() != null) // Ensure queue position is not null
                .sorted(Comparator.comparingInt(CheckIn::getQueuePosition)) // Sort by queue position
                .map(checkIn -> {
                    Patient patient = checkIn.getPatient(); // Get associated patient
                    return getCheckInResponseDto(patient, checkIn);
                })
                .collect(Collectors.toList()); // Collect results into a list
    }

    @Override
    public List<CheckIn> getCheckInsByStaffMember(String staffId) {
        User staffMember = userRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + staffId));
        return checkInRepository.findByCheckedInBy(staffMember);
    }
}

