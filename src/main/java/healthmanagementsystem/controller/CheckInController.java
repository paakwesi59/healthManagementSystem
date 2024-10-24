package healthmanagementsystem.controller;

import healthmanagementsystem.dto.CheckInResponseDto;
import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.service.CheckInService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    // Endpoint to check in a patient using registration number
    @PostMapping("/patient/registration/{registrationNumber}")
    public ResponseEntity<CheckInResponseDto> checkInPatient(
            @PathVariable String registrationNumber,
            HttpServletRequest request) {
        return new ResponseEntity<>(checkInService.checkInPatient(registrationNumber, request), HttpStatus.CREATED);
    }

    // Endpoint to check out a patient
    @PostMapping("/checkout/{checkInId}")
    public ResponseEntity<CheckIn> checkOutPatient(
            @PathVariable Long checkInId,
            HttpServletRequest request) {
        return new ResponseEntity<>(checkInService.checkOutPatient(checkInId, request), HttpStatus.OK);
    }

    // Get all active check-ins
    @GetMapping("/active")
    public ResponseEntity<List<CheckInResponseDto>> getActiveCheckIns() {
        return new ResponseEntity<>(checkInService.getActiveCheckIns(), HttpStatus.OK);
    }

    // Get all check-ins by a staff member
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<CheckIn>> getCheckInsByStaffMember(@PathVariable String staffId) {
        return new ResponseEntity<>(checkInService.getCheckInsByStaffMember(staffId), HttpStatus.OK);
    }
}
