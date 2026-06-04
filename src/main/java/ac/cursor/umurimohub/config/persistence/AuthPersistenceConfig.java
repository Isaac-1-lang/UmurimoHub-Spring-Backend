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
        basePackages = "ac.cursor.umurimohub.auth.repository",
        entityManagerFactoryRef = "authEntityManagerFactory",
        transactionManagerRef = "authTransactionManager"
)
public class AuthPersistenceConfig {

    @Bean
    public DataSource authDataSource(ModuleDataSourceProperties properties) {
        return ModulePersistenceSupport.buildDataSource(properties.getAuth());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("authDataSource") DataSource dataSource) {
        return ModulePersistenceSupport.buildEntityManagerFactory(
                builder, dataSource, "ac.cursor.umurimohub.auth.model", "auth");
    }

    @Bean(name = "jpaSharedEM_authEntityManagerFactory")
    @DependsOn("authEntityManagerFactory")
    public EntityManager jpaSharedEmAuthEntityManagerFactory(
            @Qualifier("authEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.createSharedEntityManager(entityManagerFactory);
    }

    @Bean
    @DependsOn("authEntityManagerFactory")
    public PlatformTransactionManager authTransactionManager(
            @Qualifier("authEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.buildTransactionManager(entityManagerFactory);
    }
}
