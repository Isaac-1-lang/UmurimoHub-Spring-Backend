package ac.cursor.umurimohub.auth.port.internal;

import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.auth.port.UserProvisioningPort;
import ac.cursor.umurimohub.auth.port.dto.CreateUserCommand;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.auth.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserProvisioningAdapter implements UserProvisioningPort {

    private final UserService userService;

    public UserProvisioningAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRef createUserWithCredentials(CreateUserCommand command) {
        User user = new User();
        user.setUsername(command.username());
        user.setPassword(command.password());
        user.setFullName(command.fullName());
        user.setEmail(command.email());
        user.setPhoneNumber(command.phoneNumber());
        user.setRole(User.Role.valueOf(command.role()));
        return UserLookupAdapter.toRef(userService.createUserWithEmailNotification(user));
    }
}
