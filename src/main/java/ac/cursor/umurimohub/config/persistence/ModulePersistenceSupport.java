package ac.cursor.umurimohub.config.persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class ModulePersistenceSupport {

    private ModulePersistenceSupport() {
    }

    static DataSource buildDataSource(ModuleDataSourceProperties.DataSourceProps props) {
        return DataSourceBuilder.create()
                .driverClassName(props.getDriverClassName())
                .url(props.getJdbcUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .type(HikariDataSource.class)
                .build();
    }

    static LocalContainerEntityManagerFactoryBean buildEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource,
            String packages,
            String persistenceUnit) {
        Map<String, Object> jpaProps = new HashMap<>();
        jpaProps.put("hibernate.hbm2ddl.auto", "update");
        jpaProps.put("hibernate.format_sql", "true");
        return builder
                .dataSource(dataSource)
                .packages(packages)
                .persistenceUnit(persistenceUnit)
                .properties(jpaProps)
                .build();
    }

    static EntityManagerFactory unwrap(LocalContainerEntityManagerFactoryBean factoryBean) {
        return Objects.requireNonNull(
                factoryBean.getObject(),
                "EntityManagerFactory not initialized for " + factoryBean.getPersistenceUnitName());
    }

    /** Spring Data JPA injects this shared EM into repositories (not the EMF). */
    static EntityManager createSharedEntityManager(EntityManagerFactory entityManagerFactory) {
        return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    }

    static PlatformTransactionManager buildTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    static String sharedEntityManagerBeanName(String entityManagerFactoryRef) {
        return "jpaSharedEM_" + entityManagerFactoryRef;
    }
}
