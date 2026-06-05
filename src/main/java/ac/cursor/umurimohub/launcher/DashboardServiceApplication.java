package ac.cursor.umurimohub.launcher;

import ac.cursor.umurimohub.config.ModulesInfoController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        scanBasePackages = {
                "ac.cursor.umurimohub.dashboard",
                "ac.cursor.umurimohub.shared",
                "ac.cursor.umurimohub.security",
                "ac.cursor.umurimohub.cloud",
                "ac.cursor.umurimohub.config"
        },
        exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        })
@EnableDiscoveryClient
@Import(ModulesInfoController.class)
public class DashboardServiceApplication {

    public static void main(String[] args) {
        ServiceApplicationSupport.run(DashboardServiceApplication.class, "service-dashboard", args);
    }
}
