package ac.cursor.umurimohub.payroll.dto;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryDeductionRequest {
    @NotNull(message = "Worker ID is required")
    private Long workerId;

    @NotNull(message = "Deduction date is required")
    private LocalDate deductionDate;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Deduction type is required")
    private SalaryDeduction.DeductionType type;

    @NotBlank(message = "Reason is required")
    private String reason;

    public SalaryDeductionRequest() {
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public LocalDate getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(LocalDate deductionDate) {
        this.deductionDate = deductionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalaryDeduction.DeductionType getType() {
        return type;
    }

    public void setType(SalaryDeduction.DeductionType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
