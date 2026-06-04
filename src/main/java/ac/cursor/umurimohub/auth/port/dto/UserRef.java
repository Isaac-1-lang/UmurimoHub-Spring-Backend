package ac.cursor.umurimohub.auth.port.dto;

public record UserRef(
        Long id,
        String username,
        String fullName,
        String email,
        String phoneNumber,
        String role
) {
}
