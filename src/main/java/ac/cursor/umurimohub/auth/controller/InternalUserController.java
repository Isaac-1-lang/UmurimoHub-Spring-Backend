package ac.cursor.umurimohub.auth.controller;

import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.auth.port.internal.UserLookupAdapter;
import ac.cursor.umurimohub.auth.repository.UserRepository;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@Profile("service-auth")
public class InternalUserController {

    private final UserRepository userRepository;

    public InternalUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserRef> getById(@PathVariable Long id) {
        UserRef user = userRepository.findById(id)
                .map(UserLookupAdapter::toRef)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ApiResponse.success("User", user);
    }

    @GetMapping("/by-username/{username}")
    public ApiResponse<UserRef> getByUsername(@PathVariable String username) {
        UserRef user = userRepository.findByUsername(username)
                .map(UserLookupAdapter::toRef)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ApiResponse.success("User", user);
    }
}
