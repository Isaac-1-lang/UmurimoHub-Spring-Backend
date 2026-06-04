package ac.cursor.umurimohub.auth.port;

import ac.cursor.umurimohub.auth.port.dto.UserRef;

import java.util.Optional;

public interface UserLookupPort {

    Optional<UserRef> findById(Long id);

    UserRef requireById(Long id);
}
