package ac.cursor.umurimohub.config.seed;

import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(1)
public class AuthSampleDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthSampleDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(transactionManager = "authTransactionManager")
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        saveUser("ceo", "ceo123", "example CEO", "ceo@umurimohub.ac", "+250788000001", User.Role.CEO);
        saveUser("hr", "hr123", "Jane HR Manager", "hr@umurimohub.ac", "+250788000002", User.Role.HR);
        saveUser("worker1", "worker123", "Alice Worker", "alice@umurimohub.ac", "+250788000003", User.Role.WORKER);
        saveUser("worker2", "worker123", "Bob Worker", "bob@umurimohub.ac", "+250788000004", User.Role.WORKER);
    }

    private void saveUser(String username, String password, String fullName, String email, String phone, User.Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setRole(role);
        userRepository.save(user);
    }
}
