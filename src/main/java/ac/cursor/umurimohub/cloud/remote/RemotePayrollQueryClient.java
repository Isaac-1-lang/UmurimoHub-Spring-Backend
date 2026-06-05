package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.payroll.port.PayrollQueryPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Profile("distributed & !service-payroll")
public class RemotePayrollQueryClient implements PayrollQueryPort {

    private final WebClient.Builder webClient;
    private final String payrollService;

    public RemotePayrollQueryClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.payrollService = discovery.getPayrollService();
    }

    @Override
    public List<SalaryDeduction> getAllDeductions() {
        return RemoteServiceClientSupport.getData(
                webClient,
                payrollService,
                "/internal/payroll/deductions",
                new ParameterizedTypeReference<ApiResponse<List<SalaryDeduction>>>() {});
    }
}
