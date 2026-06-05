package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.discipline.port.DisciplineQueryPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Profile("distributed & !service-discipline")
public class RemoteDisciplineQueryClient implements DisciplineQueryPort {

    private final WebClient.Builder webClient;
    private final String disciplineService;

    public RemoteDisciplineQueryClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.disciplineService = discovery.getDisciplineService();
    }

    @Override
    public List<DisciplinaryAction> getAllActions() {
        return RemoteServiceClientSupport.getData(
                webClient,
                disciplineService,
                "/internal/discipline/actions",
                new ParameterizedTypeReference<ApiResponse<List<DisciplinaryAction>>>() {});
    }
}
