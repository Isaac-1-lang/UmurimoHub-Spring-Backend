package ac.cursor.umurimohub.config.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Default profile: one database, but services still declare module-specific transaction manager names
 * so the same code works when {@code modular-db} is enabled.
 */
@Configuration
@Profile("!modular-db")
public class SingleDbTransactionManagerAliases {

    @Bean(name = {
            "authTransactionManager",
            "workerTransactionManager",
            "attendanceTransactionManager",
            "payrollTransactionManager",
            "disciplineTransactionManager"
    })
    public PlatformTransactionManager moduleTransactionManagerAliases(PlatformTransactionManager transactionManager) {
        return transactionManager;
    }
}
