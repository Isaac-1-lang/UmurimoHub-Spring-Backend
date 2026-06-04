package ac.cursor.umurimohub.auth.port.dto;

public record CreateUserCommand(
        String username,
        String password,
        String fullName,
        String email,
        String phoneNumber,
        String role
) {
}
