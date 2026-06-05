package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

final class RemoteServiceClientSupport {

    private RemoteServiceClientSupport() {
    }

    static <T> T getData(WebClient.Builder builder, String serviceId, String path, Class<T> dataType) {
        ApiResponse<T> response = builder.build()
                .get()
                .uri("http://" + serviceId + path)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<T>>() {})
                .block();
        if (response == null || response.getData() == null) {
            throw new RuntimeException("Empty response from " + serviceId + path);
        }
        return response.getData();
    }

    static <T> T getData(WebClient.Builder builder, String serviceId, String path,
                           ParameterizedTypeReference<ApiResponse<T>> typeRef) {
        ApiResponse<T> response = builder.build()
                .get()
                .uri("http://" + serviceId + path)
                .retrieve()
                .bodyToMono(typeRef)
                .block();
        if (response == null || response.getData() == null) {
            throw new RuntimeException("Empty response from " + serviceId + path);
        }
        return response.getData();
    }
}
