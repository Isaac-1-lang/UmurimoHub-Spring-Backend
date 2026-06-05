package ac.cursor.umurimohub.launcher;

import ac.cursor.umurimohub.config.persistence.SingleDbTransactionManagerAliases;
import ac.cursor.umurimohub.config.seed.AuthSampleDataLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
        "ac.cursor.umurimohub.auth",
        "ac.cursor.umurimohub.notification",
        "ac.cursor.umurimohub.shared",
        "ac.cursor.umurimohub.security",
        "ac.cursor.umurimohub.cloud"
})
@EnableDiscoveryClient
@Import({AuthSampleDataLoader.class, SingleDbTransactionManagerAliases.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        ServiceApplicationSupport.run(AuthServiceApplication.class, "service-auth", args);
    }
}
