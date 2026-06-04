package ac.cursor.umurimohub.auth.port;

import ac.cursor.umurimohub.auth.port.dto.UserRef;

public interface CurrentUserPort {

    UserRef getCurrentUser();
}
