package ac.cursor.umurimohub.config.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Profile("modular-db")
@EnableJpaRepositories(
        basePackages = "ac.cursor.umurimohub.worker.repository",
        entityManagerFactoryRef = "workerEntityManagerFactory",
        transactionManagerRef = "workerTransactionManager"
)
public class WorkerPersistenceConfig {

    @Bean
    public DataSource workerDataSource(ModuleDataSourceProperties properties) {
        return ModulePersistenceSupport.buildDataSource(properties.getWorker());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean workerEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("workerDataSource") DataSource dataSource) {
        return ModulePersistenceSupport.buildEntityManagerFactory(
                builder, dataSource, "ac.cursor.umurimohub.worker.model", "worker");
    }

    @Bean(name = "jpaSharedEM_workerEntityManagerFactory")
    @DependsOn("workerEntityManagerFactory")
    public EntityManager jpaSharedEmWorkerEntityManagerFactory(
            @Qualifier("workerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.createSharedEntityManager(entityManagerFactory);
    }

    @Bean
    @DependsOn("workerEntityManagerFactory")
    public PlatformTransactionManager workerTransactionManager(
            @Qualifier("workerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.buildTransactionManager(entityManagerFactory);
    }
}
