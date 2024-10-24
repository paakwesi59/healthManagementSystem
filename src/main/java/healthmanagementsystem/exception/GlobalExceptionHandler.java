package healthmanagementsystem.exception;

import healthmanagementsystem.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static healthmanagementsystem.util.StringConstant.ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserInvitationException.class)
    public ResponseEntity<ErrorResponse> handleUserInvitationException(UserInvitationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PatientAlreadyCheckedInException.class)
    public ResponseEntity<ErrorResponse> handlePatientAlreadyCheckedInException(PatientAlreadyCheckedInException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // Other exception handlers...
}
