//package healthmanagementsystem.service;
//
//import healthmanagementsystem.model.CheckIn;
//import healthmanagementsystem.model.Patient;
//import healthmanagementsystem.model.User;
//
//import java.util.List;
//
//public interface CheckInService {
//
//    CheckIn checkInPatient(Patient patient, User staffMember);
//    CheckIn checkOutPatient(Long checkInId, User staffMember);
//    List<CheckIn> getActiveCheckIns();
//    List<CheckIn> getCheckInsByStaffMember(User staffMember);
//}

package healthmanagementsystem.service;

import healthmanagementsystem.dto.CheckInResponseDto;
import healthmanagementsystem.model.CheckIn;

import java.util.List;

public interface CheckInService {
    CheckInResponseDto checkInPatient(String registrationNumber, String staffId); // Accept registration number and staff ID directly
    CheckIn checkOutPatient(Long checkInId, String staffId); // Accept staff ID as a string
    List<CheckInResponseDto> getActiveCheckIns(); // Updated to return a list of CheckInResponseDto
    List<CheckIn> getCheckInsByStaffMember(String staffId); // Accept staff ID as a string
}
