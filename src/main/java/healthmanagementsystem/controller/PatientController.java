//package healthmanagementsystem.controller;
//
//import healthmanagementsystem.dto.PatientDto;
//import healthmanagementsystem.dto.ResponseMessage;
//import healthmanagementsystem.service.PatientService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import static healthmanagementsystem.util.StringConstant.SUCCESS;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/patients")
//public class PatientController {
//
//    private final PatientService patientService;
//
//    @PostMapping("/register")
//    public ResponseEntity<ResponseMessage> registerPatient(@RequestBody @Valid PatientDto patientDto) {
//        ResponseMessage response = patientService.registerPatient(patientDto);
//        return new ResponseEntity<>(response, response.getStatus().equalsIgnoreCase(SUCCESS) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseMessage> getPatientById(@PathVariable String id) {
//        ResponseMessage response = patientService.findPatientById(id);
//        return new ResponseEntity<>(response, response.getStatus().equalsIgnoreCase(SUCCESS) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
//    }
//}

package healthmanagementsystem.controller;

import healthmanagementsystem.dto.PatientDto;
import healthmanagementsystem.dto.PatientResponseMessage;
import healthmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static healthmanagementsystem.util.StringConstant.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<PatientResponseMessage> registerPatient(@RequestBody @Valid PatientDto patientDto) {
        return new ResponseEntity<>(patientService.registerPatient(patientDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseMessage> getPatientById(@PathVariable String id) {
        return new ResponseEntity<>(patientService.findPatientById(id),
                patientService.findPatientById(id).getStatus().equalsIgnoreCase(SUCCESS) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/registration/{registrationNumber}")  // New endpoint for searching by registration number
    public ResponseEntity<PatientResponseMessage> getPatientByRegistrationNumber(@PathVariable String registrationNumber) {
        return new ResponseEntity<>(patientService.findPatientByRegistrationNumber(registrationNumber),
                patientService.findPatientByRegistrationNumber(registrationNumber).getStatus().equalsIgnoreCase(SUCCESS) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}

