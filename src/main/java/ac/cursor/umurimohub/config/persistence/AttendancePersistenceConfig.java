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
        basePackages = "ac.cursor.umurimohub.attendance.repository",
        entityManagerFactoryRef = "attendanceEntityManagerFactory",
        transactionManagerRef = "attendanceTransactionManager"
)
public class AttendancePersistenceConfig {

    @Bean
    public DataSource attendanceDataSource(ModuleDataSourceProperties properties) {
        return ModulePersistenceSupport.buildDataSource(properties.getAttendance());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean attendanceEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("attendanceDataSource") DataSource dataSource) {
        return ModulePersistenceSupport.buildEntityManagerFactory(
                builder, dataSource, "ac.cursor.umurimohub.attendance.model", "attendance");
    }

    @Bean(name = "jpaSharedEM_attendanceEntityManagerFactory")
    @DependsOn("attendanceEntityManagerFactory")
    public EntityManager jpaSharedEmAttendanceEntityManagerFactory(
            @Qualifier("attendanceEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.createSharedEntityManager(entityManagerFactory);
    }

    @Bean
    @DependsOn("attendanceEntityManagerFactory")
    public PlatformTransactionManager attendanceTransactionManager(
            @Qualifier("attendanceEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return ModulePersistenceSupport.buildTransactionManager(entityManagerFactory);
    }
}
