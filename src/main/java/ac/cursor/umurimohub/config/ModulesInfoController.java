package ac.cursor.umurimohub.config;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Modules", description = "Logical service modules and deployment topology")
@EnableConfigurationProperties(ModuleRegistryProperties.class)
public class ModulesInfoController {

    private final ModuleRegistryProperties moduleRegistry;
    private final boolean modularDbEnabled;
    private final boolean distributedEnabled;
    private final int gatewayPort;
    private final String eurekaUrl;

    public ModulesInfoController(
            ModuleRegistryProperties moduleRegistry,
            @Value("${app.multi-database.enabled:false}") boolean modularDbEnabled,
            @Value("${app.distributed.enabled:false}") boolean distributedEnabled,
            @Value("${app.api-gateway.port:8080}") int gatewayPort,
            @Value("${eureka.client.service-url.defaultZone:http://localhost:8761/eureka/}") String eurekaUrl) {
        this.moduleRegistry = moduleRegistry;
        this.modularDbEnabled = modularDbEnabled;
        this.distributedEnabled = distributedEnabled;
        this.gatewayPort = gatewayPort;
        this.eurekaUrl = eurekaUrl;
    }

    @GetMapping
    @Operation(summary = "List logical modules", description = "Shows ports, databases, and Eureka discovery info")
    public ApiResponse<Map<String, Object>> listModules() {
        Map<String, Object> payload = new LinkedHashMap<>();
        if (distributedEnabled) {
            payload.put("deploymentMode", "distributed-services");
        } else if (modularDbEnabled) {
            payload.put("deploymentMode", "modular-databases");
        } else {
            payload.put("deploymentMode", "monolith");
        }
        payload.put("apiGatewayPort", gatewayPort);
        payload.put("eurekaRegistry", eurekaUrl);
        payload.put("modules", moduleRegistry.getRegistry());
        return ApiResponse.success("Module registry", payload);
    }
}
