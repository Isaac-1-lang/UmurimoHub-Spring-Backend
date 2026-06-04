package ac.cursor.umurimohub.auth.port;

import ac.cursor.umurimohub.auth.port.dto.CreateUserCommand;
import ac.cursor.umurimohub.auth.port.dto.UserRef;

public interface UserProvisioningPort {

    UserRef createUserWithCredentials(CreateUserCommand command);
}
