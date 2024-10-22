package healthmanagementsystem.service;

import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;

import java.util.List;

public interface CheckInService {

    CheckIn checkInPatient(Patient patient, User staffMember);
    CheckIn checkOutPatient(Long checkInId, User staffMember);
    List<CheckIn> getActiveCheckIns();
    List<CheckIn> getCheckInsByStaffMember(User staffMember);
}
