package ac.cursor.umurimohub.notification.service;

import ac.cursor.umurimohub.notification.port.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements NotificationPort {

    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String frontendUrl;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String fromEmail,
            @Value("${app.frontend.url:http://localhost:8080}") String frontendUrl) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("UmurimoHub - Email Verification OTP");
        message.setText(String.format(
                "Hello,\n\n" +
                "Your OTP code for UmurimoHub verification is: %s\n\n" +
                "This code will expire in 10 minutes.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Best regards,\n" +
                "UmurimoHub Team",
                otpCode
        ));

        mailSender.send(message);
    }

    @Override
    public void sendCredentialsEmail(String fullName, String email, String username, String plainPassword, String role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("UmurimoHub - Your Account Credentials");
        message.setText(String.format(
                "Dear %s,\n\n" +
                "Your UmurimoHub account has been created.\n\n" +
                "Login credentials:\n" +
                "  Username: %s\n" +
                "  Password: %s\n" +
                "  Role: %s\n\n" +
                "Sign in at: %s/login\n\n" +
                "After signing in, you will receive a one-time code (OTP) at this email address to complete login.\n\n" +
                "Please change your password after your first login.\n\n" +
                "Best regards,\n" +
                "UmurimoHub Team",
                fullName,
                username,
                plainPassword,
                role,
                frontendUrl.replaceAll("/$", "")
        ));

        mailSender.send(message);
    }
}
