package ac.cursor.umurimohub.payroll.port.internal;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.payroll.port.PayrollQueryPort;
import ac.cursor.umurimohub.payroll.service.SalaryDeductionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!distributed & !service-payroll")
public class PayrollQueryAdapter implements PayrollQueryPort {

    private final SalaryDeductionService salaryDeductionService;

    public PayrollQueryAdapter(SalaryDeductionService salaryDeductionService) {
        this.salaryDeductionService = salaryDeductionService;
    }

    @Override
    public List<SalaryDeduction> getAllDeductions() {
        return salaryDeductionService.getAllDeductions();
    }
}
