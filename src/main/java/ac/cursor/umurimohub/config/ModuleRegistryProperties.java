package ac.cursor.umurimohub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app.modules")
public class ModuleRegistryProperties {

    private final Map<String, ModuleDescriptor> registry = new LinkedHashMap<>();

    public Map<String, ModuleDescriptor> getRegistry() {
        return registry;
    }

    public static class ModuleDescriptor {
        private int port;
        private String database;
        private String description;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
