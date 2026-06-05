package ac.cursor.umurimohub.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.discovery")
public class ServiceDiscoveryProperties {

    /** Eureka service id for auth (spring.application.name). */
    private String authService = "auth-service";
    private String workerService = "worker-service";
    private String attendanceService = "attendance-service";
    private String payrollService = "payroll-service";
    private String disciplineService = "discipline-service";
    private String dashboardService = "dashboard-service";

    public String getAuthService() {
        return authService;
    }

    public void setAuthService(String authService) {
        this.authService = authService;
    }

    public String getWorkerService() {
        return workerService;
    }

    public void setWorkerService(String workerService) {
        this.workerService = workerService;
    }

    public String getAttendanceService() {
        return attendanceService;
    }

    public void setAttendanceService(String attendanceService) {
        this.attendanceService = attendanceService;
    }

    public String getPayrollService() {
        return payrollService;
    }

    public void setPayrollService(String payrollService) {
        this.payrollService = payrollService;
    }

    public String getDisciplineService() {
        return disciplineService;
    }

    public void setDisciplineService(String disciplineService) {
        this.disciplineService = disciplineService;
    }

    public String getDashboardService() {
        return dashboardService;
    }

    public void setDashboardService(String dashboardService) {
        this.dashboardService = dashboardService;
    }
}
