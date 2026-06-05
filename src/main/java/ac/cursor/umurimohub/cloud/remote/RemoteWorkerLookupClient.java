package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@Component
@Profile("distributed & !service-worker")
public class RemoteWorkerLookupClient implements WorkerLookupPort {

    private final WebClient.Builder webClient;
    private final String workerService;

    public RemoteWorkerLookupClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.workerService = discovery.getWorkerService();
    }

    @Override
    public Optional<WorkerRef> findById(Long id) {
        try {
            return Optional.of(requireById(id));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<WorkerRef> findByUserId(Long userId) {
        try {
            return Optional.of(requireByUserId(userId));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    @Override
    public WorkerRef requireById(Long id) {
        return RemoteServiceClientSupport.getData(
                webClient,
                workerService,
                "/internal/workers/" + id,
                new ParameterizedTypeReference<ApiResponse<WorkerRef>>() {});
    }

    @Override
    public WorkerRef requireByUserId(Long userId) {
        return RemoteServiceClientSupport.getData(
                webClient,
                workerService,
                "/internal/workers/by-user/" + userId,
                new ParameterizedTypeReference<ApiResponse<WorkerRef>>() {});
    }

    @Override
    public List<WorkerRef> findAll() {
        return RemoteServiceClientSupport.getData(
                webClient,
                workerService,
                "/internal/workers",
                new ParameterizedTypeReference<ApiResponse<List<WorkerRef>>>() {});
    }

    @Override
    public long countWorkers() {
        return RemoteServiceClientSupport.getData(
                webClient,
                workerService,
                "/internal/workers/count",
                new ParameterizedTypeReference<ApiResponse<Long>>() {});
    }

    @Override
    public void assertExists(Long workerId) {
        try {
            webClient.build()
                    .get()
                    .uri("http://" + workerService + "/internal/workers/" + workerId + "/exists")
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new RuntimeException("Worker not found");
        }
    }
}
