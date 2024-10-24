package healthmanagementsystem.service;

import healthmanagementsystem.dto.CheckInResponseDto;
import healthmanagementsystem.model.CheckIn;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CheckInService {
    CheckInResponseDto checkInPatient(String registrationNumber, HttpServletRequest request); // Accept registration number and HttpServletRequest
    CheckIn checkOutPatient(Long checkInId, HttpServletRequest request); // Accept HttpServletRequest


    List<CheckInResponseDto> getActiveCheckIns(); // Updated to return a list of CheckInResponseDto
    List<CheckIn> getCheckInsByStaffMember(String staffId); // Accept staff ID as a string
}
