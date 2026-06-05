package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.auth.port.UserProvisioningPort;
import ac.cursor.umurimohub.auth.port.dto.CreateUserCommand;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("distributed & service-worker")
public class RemoteUserProvisioningClient implements UserProvisioningPort {

    private final WebClient.Builder webClient;
    private final String authService;

    public RemoteUserProvisioningClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.authService = discovery.getAuthService();
    }

    @Override
    public UserRef createUserWithCredentials(CreateUserCommand command) {
        ApiResponse<UserRef> response = webClient.build()
                .post()
                .uri("http://" + authService + "/internal/users")
                .bodyValue(command)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserRef>>() {})
                .block();
        if (response == null || response.getData() == null) {
            throw new RuntimeException("Failed to create user via auth-service");
        }
        return response.getData();
    }
}
