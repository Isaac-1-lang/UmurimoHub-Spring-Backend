package ac.cursor.umurimohub.launcher;

import ac.cursor.umurimohub.config.persistence.SingleDbTransactionManagerAliases;
import ac.cursor.umurimohub.config.seed.DistributedWorkerSampleDataLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
        "ac.cursor.umurimohub.worker",
        "ac.cursor.umurimohub.shared",
        "ac.cursor.umurimohub.security",
        "ac.cursor.umurimohub.cloud"
})
@EnableDiscoveryClient
@Import({DistributedWorkerSampleDataLoader.class, SingleDbTransactionManagerAliases.class})
public class WorkerServiceApplication {

    public static void main(String[] args) {
        ServiceApplicationSupport.run(WorkerServiceApplication.class, "service-worker", args);
    }
}
