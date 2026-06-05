package ac.cursor.umurimohub.payroll.port;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;

import java.util.List;

public interface PayrollQueryPort {
    List<SalaryDeduction> getAllDeductions();
}
