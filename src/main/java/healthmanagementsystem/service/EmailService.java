package healthmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class EmailService {
   private final JavaMailSender mailSender;


public void sendInvitationEmail(String to, String firstName, String lastName, String role, String staffNumber, String tempPassword) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject("Welcome to the Clinic Management System");

    String body = "Dear " + firstName + " " + lastName + ",\n\n" +
            "You have been invited to the Clinic Management System.\n" +
            "Your role: " + role + "\n" +
            "Your Staff Number: " + staffNumber + "\n" +
            "Temporary Password: " + tempPassword + "\n" +
            "Please log in and change your password upon first login.\n\n" +
            "Best regards,\nClinic Management Team";

    message.setText(body);
    message.setFrom("asmahpaakwesi59@gmail.com");

    mailSender.send(message);
}
}

