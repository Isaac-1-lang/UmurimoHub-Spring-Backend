package ac.cursor.umurimohub.auth.port.internal;

import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.auth.port.UserLookupPort;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.auth.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserLookupAdapter implements UserLookupPort {

    private final UserRepository userRepository;

    public UserLookupAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserRef> findById(Long id) {
        return userRepository.findById(id).map(UserLookupAdapter::toRef);
    }

    @Override
    public UserRef requireById(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    static UserRef toRef(User user) {
        return new UserRef(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().name()
        );
    }
}
