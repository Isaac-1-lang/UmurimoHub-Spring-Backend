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
        basePackages = "ac.cursor.umurimohub.discipline.repository",
        entityManagerFactoryRef = "disciplineEntityManagerFactory",
        transactionManagerRef = "disciplineTransactionManager"
)
public class DisciplinePersistenceConfig {

    @Bean
    public DataSource disciplineDataSource(ModuleDataSourceProperties properties) {
        return ModulePersistenceSupport.buildDataSource(properties.getDiscipline());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean disciplineEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("disciplineDataSource") DataSource dataSource) {
        return ModulePersistenceSupport.buildEntityManagerFactory(
                builder, dataSource, "ac.cursor.umurimohub.discipline.model", "discipline");
    }

    @Bean(name = "jpaSharedEM_disciplineEntityManagerFactory")
    @DependsOn("disciplineEntityManagerFactory")
    public EntityManager jpaSharedEmDisciplineEntityManagerFactory(
            @Qualifier("disciplineEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.createSharedEntityManager(entityManagerFactory);
    }

    @Bean
    @DependsOn("disciplineEntityManagerFactory")
    public PlatformTransactionManager disciplineTransactionManager(
            @Qualifier("disciplineEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.buildTransactionManager(entityManagerFactory);
    }
}
