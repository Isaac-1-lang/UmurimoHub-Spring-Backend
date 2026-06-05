package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("distributed & !service-auth")
public class RemoteCurrentUserClient implements CurrentUserPort {

    private final WebClient.Builder webClient;
    private final String authService;

    public RemoteCurrentUserClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.authService = discovery.getAuthService();
    }

    @Override
    public UserRef getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return RemoteServiceClientSupport.getData(
                webClient,
                authService,
                "/internal/users/by-username/" + username,
                new ParameterizedTypeReference<ApiResponse<UserRef>>() {});
    }
}
