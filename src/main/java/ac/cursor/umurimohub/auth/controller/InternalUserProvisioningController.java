package ac.cursor.umurimohub.auth.controller;

import ac.cursor.umurimohub.auth.port.UserProvisioningPort;
import ac.cursor.umurimohub.auth.port.dto.CreateUserCommand;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@Profile("service-auth")
public class InternalUserProvisioningController {

    private final UserProvisioningPort userProvisioningPort;

    public InternalUserProvisioningController(UserProvisioningPort userProvisioningPort) {
        this.userProvisioningPort = userProvisioningPort;
    }

    @PostMapping
    public ApiResponse<UserRef> create(@Valid @RequestBody CreateUserCommand command) {
        return ApiResponse.success("User created", userProvisioningPort.createUserWithCredentials(command));
    }
}
