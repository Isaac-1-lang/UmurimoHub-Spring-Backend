package ac.cursor.umurimohub.config.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.datasource")
public class ModuleDataSourceProperties {

    private final DataSourceProps auth = new DataSourceProps();
    private final DataSourceProps worker = new DataSourceProps();
    private final DataSourceProps attendance = new DataSourceProps();
    private final DataSourceProps payroll = new DataSourceProps();
    private final DataSourceProps discipline = new DataSourceProps();

    public DataSourceProps getAuth() {
        return auth;
    }

    public DataSourceProps getWorker() {
        return worker;
    }

    public DataSourceProps getAttendance() {
        return attendance;
    }

    public DataSourceProps getPayroll() {
        return payroll;
    }

    public DataSourceProps getDiscipline() {
        return discipline;
    }

    public static class DataSourceProps {
        private String jdbcUrl;
        private String username;
        private String password;
        private String driverClassName = "org.postgresql.Driver";

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public void setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
}
