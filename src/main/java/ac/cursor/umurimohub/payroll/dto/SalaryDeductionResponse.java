package ac.cursor.umurimohub.payroll.dto;

import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryDeductionResponse(
        Long id,
        LocalDate deductionDate,
        BigDecimal amount,
        SalaryDeduction.DeductionType type,
        String reason,
        WorkerRef worker,
        UserRef approvedBy
) {
    public static SalaryDeductionResponse from(SalaryDeduction deduction, WorkerRef worker, UserRef approvedBy) {
        return new SalaryDeductionResponse(
                deduction.getId(),
                deduction.getDeductionDate(),
                deduction.getAmount(),
                deduction.getType(),
                deduction.getReason(),
                worker,
                approvedBy
        );
    }
}
