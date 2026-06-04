package ac.cursor.umurimohub;

import ac.cursor.umurimohub.config.ModuleRegistryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ModuleRegistryProperties.class)
public class UmurimoHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmurimoHubApplication.class, args);
    }
}
