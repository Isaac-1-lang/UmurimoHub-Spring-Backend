package ac.cursor.umurimohub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "UmurimoHub Workforce Management API",
                version = "1.0.0",
                description = """
                        Comprehensive workforce management system REST API with role-based access control.
                        
                        **Features:**
                        - Worker management (HR)
                        - Attendance tracking (HR)
                        - Salary deductions management (HR)
                        - Disciplinary actions management (HR)
                        - Operational summaries (CEO)
                        - Worker self-service portal (Workers)
                        
                        **Roles:**
                        - **CEO**: View-only access to all operational data and summaries
                        - **HR**: Full CRUD access to workers, attendance, deductions, and disciplinary actions
                        - **WORKER**: View-only access to personal attendance, deductions, and disciplinary records
                        """,
                contact = @Contact(
                        name = "UmurimoHub Support",
                        email = "support@umurimohub.ac"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Production Server",
                        url = "https://api.umurimohub.ac"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer Token Authentication. Login or Register to get your token, then use it in the format: Bearer <token>",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
