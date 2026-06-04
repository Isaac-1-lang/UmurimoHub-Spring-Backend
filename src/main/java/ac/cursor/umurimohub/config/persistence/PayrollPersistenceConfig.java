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
        basePackages = "ac.cursor.umurimohub.payroll.repository",
        entityManagerFactoryRef = "payrollEntityManagerFactory",
        transactionManagerRef = "payrollTransactionManager"
)
public class PayrollPersistenceConfig {

    @Bean
    public DataSource payrollDataSource(ModuleDataSourceProperties properties) {
        return ModulePersistenceSupport.buildDataSource(properties.getPayroll());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean payrollEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("payrollDataSource") DataSource dataSource) {
        return ModulePersistenceSupport.buildEntityManagerFactory(
                builder, dataSource, "ac.cursor.umurimohub.payroll.model", "payroll");
    }

    @Bean(name = "jpaSharedEM_payrollEntityManagerFactory")
    @DependsOn("payrollEntityManagerFactory")
    public EntityManager jpaSharedEmPayrollEntityManagerFactory(
            @Qualifier("payrollEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.createSharedEntityManager(entityManagerFactory);
    }

    @Bean
    @DependsOn("payrollEntityManagerFactory")
    public PlatformTransactionManager payrollTransactionManager(
            @Qualifier("payrollEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.buildTransactionManager(entityManagerFactory);
    }
}
