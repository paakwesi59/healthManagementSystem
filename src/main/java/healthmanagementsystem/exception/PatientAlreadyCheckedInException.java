package healthmanagementsystem.exception;

public class PatientAlreadyCheckedInException extends RuntimeException {
    public PatientAlreadyCheckedInException(String message) {
        super(message);
    }
}
