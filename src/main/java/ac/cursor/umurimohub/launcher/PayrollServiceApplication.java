package ac.cursor.umurimohub.launcher;

import ac.cursor.umurimohub.config.persistence.SingleDbTransactionManagerAliases;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
        "ac.cursor.umurimohub.payroll",
        "ac.cursor.umurimohub.shared",
        "ac.cursor.umurimohub.security",
        "ac.cursor.umurimohub.cloud"
})
@EnableDiscoveryClient
@Import(SingleDbTransactionManagerAliases.class)
public class PayrollServiceApplication {

    public static void main(String[] args) {
        ServiceApplicationSupport.run(PayrollServiceApplication.class, "service-payroll", args);
    }
}
