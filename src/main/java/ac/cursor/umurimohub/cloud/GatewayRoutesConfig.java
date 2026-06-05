package ac.cursor.umurimohub.cloud;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("api-gateway")
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**", "/api/users/**")
                        .uri("lb://auth-service"))
                .route("worker-service", r -> r.path("/api/workers/**")
                        .uri("lb://worker-service"))
                .route("attendance-service", r -> r.path("/api/attendance/**", "/api/reactive/**")
                        .uri("lb://attendance-service"))
                .route("payroll-service", r -> r.path("/api/salary-deductions/**")
                        .uri("lb://payroll-service"))
                .route("discipline-service", r -> r.path("/api/disciplinary-actions/**")
                        .uri("lb://discipline-service"))
                .route("dashboard-service", r -> r.path("/api/dashboard/**", "/api/modules")
                        .uri("lb://dashboard-service"))
                .build();
    }
}
