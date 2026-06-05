package ac.cursor.umurimohub.auth.port.internal;

import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.auth.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!distributed")
public class CurrentUserAdapter implements CurrentUserPort {

    private final UserService userService;

    public CurrentUserAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRef getCurrentUser() {
        return UserLookupAdapter.toRef(userService.getCurrentUser());
    }
}
