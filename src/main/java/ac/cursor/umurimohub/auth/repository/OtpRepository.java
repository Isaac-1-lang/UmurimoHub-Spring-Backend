package ac.cursor.umurimohub.auth.repository;

import ac.cursor.umurimohub.auth.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndOtpCodeAndVerifiedFalse(String email, String otpCode);
    Optional<Otp> findTopByEmailAndVerifiedFalseOrderByCreatedAtDesc(String email);
    void deleteByEmail(String email);
}
