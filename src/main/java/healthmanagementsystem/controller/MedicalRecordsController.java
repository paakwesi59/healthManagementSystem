package healthmanagementsystem.controller;

import healthmanagementsystem.dto.MedicalRecordsRequest;
import healthmanagementsystem.model.MedicalRecords;
import healthmanagementsystem.service.MedicalRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordsController {

    private final MedicalRecordsService medicalRecordsService;

    @PostMapping
    public ResponseEntity<MedicalRecords> createMedicalRecord(@RequestBody MedicalRecordsRequest request) {
        MedicalRecords medicalRecord = medicalRecordsService.createMedicalRecord(request);
        return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecords> updateMedicalRecord(
            @PathVariable String id, @RequestBody MedicalRecordsRequest request) {
        MedicalRecords medicalRecord = medicalRecordsService.updateMedicalRecord(id, request);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecords> getMedicalRecordById(@PathVariable String id) {
        MedicalRecords medicalRecord = medicalRecordsService.getMedicalRecordById(id);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecords>> getMedicalRecordsByPatientId(@PathVariable String patientId) {
        List<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecordsByPatientId(patientId);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<MedicalRecords>> getMedicalRecordsByDoctorId(@PathVariable String doctorId) {
        List<MedicalRecords> medicalRecords = medicalRecordsService.getMedicalRecordsByDoctorId(doctorId);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String id) {
        medicalRecordsService.deleteMedicalRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
