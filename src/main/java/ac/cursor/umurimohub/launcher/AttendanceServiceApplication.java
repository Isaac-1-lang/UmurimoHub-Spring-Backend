package ac.cursor.umurimohub.launcher;

import ac.cursor.umurimohub.config.persistence.SingleDbTransactionManagerAliases;
import ac.cursor.umurimohub.config.seed.DistributedAttendanceSampleDataLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
        "ac.cursor.umurimohub.attendance",
        "ac.cursor.umurimohub.shared",
        "ac.cursor.umurimohub.security",
        "ac.cursor.umurimohub.cloud"
})
@EnableDiscoveryClient
@Import({DistributedAttendanceSampleDataLoader.class, SingleDbTransactionManagerAliases.class})
public class AttendanceServiceApplication {

    public static void main(String[] args) {
        ServiceApplicationSupport.run(AttendanceServiceApplication.class, "service-attendance", args);
    }
}
