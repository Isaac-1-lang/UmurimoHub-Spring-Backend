package ac.cursor.umurimohub.notification.port;

public interface NotificationPort {

    void sendOtpEmail(String toEmail, String otpCode);

    void sendCredentialsEmail(String fullName, String email, String username, String plainPassword, String role);
}
