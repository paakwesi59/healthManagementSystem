package healthmanagementsystem.controller;

import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import healthmanagementsystem.service.CheckInService;
import healthmanagementsystem.service.PatientService;
import healthmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    // Endpoint to check in a patient
    @PostMapping("/patient/{patientId}/staff/{staffId}")
    public CheckIn checkInPatient(@PathVariable String patientId, @PathVariable String staffId) {
        return checkInService.checkInPatient(
                patientService.getPatientById(patientId),
                userService.getUserById(staffId)
        );
    }

    // Endpoint to check out a patient
    @PostMapping("/checkout/{checkInId}/staff/{staffId}")
    public CheckIn checkOutPatient(@PathVariable Long checkInId, @PathVariable String staffId) {
        return checkInService.checkOutPatient(checkInId, userService.getUserById(staffId));
    }

    // Get all active check-ins
    @GetMapping("/active")
    public List<CheckIn> getActiveCheckIns() {
        return checkInService.getActiveCheckIns();
    }

    // Get all check-ins by a staff member
    @GetMapping("/staff/{staffId}")
    public List<CheckIn> getCheckInsByStaffMember(@PathVariable String staffId) {
        return checkInService.getCheckInsByStaffMember(userService.getUserById(staffId));
    }
}
