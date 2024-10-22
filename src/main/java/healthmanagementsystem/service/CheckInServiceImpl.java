package healthmanagementsystem.service;

import healthmanagementsystem.model.CheckIn;
import healthmanagementsystem.model.CheckInStatus;
import healthmanagementsystem.model.Patient;
import healthmanagementsystem.model.User;
import healthmanagementsystem.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Override
    public CheckIn checkInPatient(Patient patient, User staffMember) {
        CheckIn checkIn = new CheckIn();
        checkIn.setPatient(patient);
        checkIn.setCheckedInBy(staffMember);
        checkIn.setCheckInTime(LocalDateTime.now());
        checkIn.setStatus(CheckInStatus.WAITING);

        return checkInRepository.save(checkIn);
    }

    @Override
    public CheckIn checkOutPatient(Long checkInId, User staffMember) {
        Optional<CheckIn> checkInOptional = checkInRepository.findById(checkInId);
        if (checkInOptional.isPresent()) {
            CheckIn checkIn = checkInOptional.get();
            checkIn.setCheckOutTime(LocalDateTime.now());
            checkIn.setStatus(CheckInStatus.DISCHARGED);
            return checkInRepository.save(checkIn);
        } else {
            throw new RuntimeException("Check-in record not found for id: " + checkInId);
        }
    }

    @Override
    public List<CheckIn> getActiveCheckIns() {
        return checkInRepository.findByStatusAndCheckOutTimeIsNull(CheckInStatus.WAITING);
    }

    @Override
    public List<CheckIn> getCheckInsByStaffMember(User staffMember) {
        return checkInRepository.findByCheckedInBy(staffMember);
    }
}
