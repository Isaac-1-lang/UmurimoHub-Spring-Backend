package ac.cursor.umurimohub.payroll.controller;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.payroll.port.PayrollQueryPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/payroll")
@Profile("service-payroll")
public class InternalPayrollController {

    private final PayrollQueryPort payrollQueryPort;

    public InternalPayrollController(PayrollQueryPort payrollQueryPort) {
        this.payrollQueryPort = payrollQueryPort;
    }

    @GetMapping("/deductions")
    public ApiResponse<List<SalaryDeduction>> getAllDeductions() {
        return ApiResponse.success("Deductions", payrollQueryPort.getAllDeductions());
    }
}
