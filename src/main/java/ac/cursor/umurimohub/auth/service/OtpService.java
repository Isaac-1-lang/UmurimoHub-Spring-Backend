package ac.cursor.umurimohub.auth.service;

import ac.cursor.umurimohub.auth.model.Otp;
import ac.cursor.umurimohub.auth.repository.OtpRepository;
import ac.cursor.umurimohub.notification.port.NotificationPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final NotificationPort notificationPort;

    public OtpService(OtpRepository otpRepository, NotificationPort notificationPort) {
        this.otpRepository = otpRepository;
        this.notificationPort = notificationPort;
    }

    @Transactional
    public void generateAndSendOtp(String email) {
        // Generate 6-digit OTP
        String otpCode = String.format("%06d", new Random().nextInt(999999));
        
        // Set expiry time to 10 minutes from now
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);
        
        // Create and save OTP
        Otp otp = new Otp(email, otpCode, expiryTime);
        otpRepository.save(otp);
        
        // Send OTP via email
        notificationPort.sendOtpEmail(email, otpCode);
    }

    @Transactional
    public boolean verifyOtp(String email, String otpCode) {
        Otp otp = otpRepository.findByEmailAndOtpCodeAndVerifiedFalse(email, otpCode)
                .orElseThrow(() -> new RuntimeException("Invalid OTP code"));

        if (otp.isExpired()) {
            throw new RuntimeException("OTP has expired");
        }

        otp.setVerified(true);
        otpRepository.save(otp);
        return true;
    }

    public boolean isEmailVerified(String email) {
        return otpRepository.findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(email)
                .map(Otp::isVerified)
                .orElse(false);
    }
}
