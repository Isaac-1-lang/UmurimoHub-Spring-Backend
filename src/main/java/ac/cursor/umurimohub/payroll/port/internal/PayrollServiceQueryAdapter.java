package ac.cursor.umurimohub.payroll.port.internal;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.payroll.port.PayrollQueryPort;
import ac.cursor.umurimohub.payroll.service.SalaryDeductionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("service-payroll")
public class PayrollServiceQueryAdapter implements PayrollQueryPort {

    private final SalaryDeductionService salaryDeductionService;

    public PayrollServiceQueryAdapter(SalaryDeductionService salaryDeductionService) {
        this.salaryDeductionService = salaryDeductionService;
    }

    @Override
    public List<SalaryDeduction> getAllDeductions() {
        return salaryDeductionService.getAllDeductions();
    }
}
