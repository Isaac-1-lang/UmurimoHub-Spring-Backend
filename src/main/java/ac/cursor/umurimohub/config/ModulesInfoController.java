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
@Tag(name = "Modules", description = "Logical service modules (future extraction targets)")
@EnableConfigurationProperties(ModuleRegistryProperties.class)
public class ModulesInfoController {

    private final ModuleRegistryProperties moduleRegistry;
    private final boolean modularDbEnabled;
    private final int gatewayPort;

    public ModulesInfoController(
            ModuleRegistryProperties moduleRegistry,
            @Value("${app.multi-database.enabled:false}") boolean modularDbEnabled,
            @Value("${server.port:8081}") int gatewayPort) {
        this.moduleRegistry = moduleRegistry;
        this.modularDbEnabled = modularDbEnabled;
        this.gatewayPort = gatewayPort;
    }

    @GetMapping
    @Operation(summary = "List logical modules", description = "Shows independent DB/port assignment per bounded context")
    public ApiResponse<Map<String, Object>> listModules() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("deploymentMode", modularDbEnabled ? "modular-databases" : "shared-database");
        payload.put("apiGatewayPort", gatewayPort);
        payload.put("modules", moduleRegistry.getRegistry());
        return ApiResponse.success("Module registry", payload);
    }
}
