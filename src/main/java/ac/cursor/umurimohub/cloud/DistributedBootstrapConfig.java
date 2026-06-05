package ac.cursor.umurimohub.cloud;

import ac.cursor.umurimohub.config.ModuleRegistryProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("distributed")
@EnableConfigurationProperties({ServiceDiscoveryProperties.class, ModuleRegistryProperties.class})
public class DistributedBootstrapConfig {
}
