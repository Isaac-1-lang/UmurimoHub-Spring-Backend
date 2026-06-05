package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.auth.port.UserLookupPort;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@Profile("distributed & !service-auth")
public class RemoteUserLookupClient implements UserLookupPort {

    private final WebClient.Builder webClient;
    private final String authService;

    public RemoteUserLookupClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.authService = discovery.getAuthService();
    }

    @Override
    public Optional<UserRef> findById(Long id) {
        try {
            return Optional.of(requireById(id));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    @Override
    public UserRef requireById(Long id) {
        return RemoteServiceClientSupport.getData(
                webClient,
                authService,
                "/internal/users/" + id,
                new ParameterizedTypeReference<ApiResponse<UserRef>>() {});
    }
}
