package healthmanagementsystem.exception;

import healthmanagementsystem.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static healthmanagementsystem.util.StringConstant.ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserInvitationException.class)
    public ResponseEntity<ResponseMessage> handleUserInvitationException(UserInvitationException ex) {
        ResponseMessage responseMessage = new ResponseMessage(ERROR, ex.getMessage(), null);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
