package ac.cursor.umurimohub.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salary_deductions")
public class SalaryDeduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate deductionDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeductionType type;

    @Column(nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    public SalaryDeduction() {
    }

    public SalaryDeduction(Long id, Worker worker, LocalDate deductionDate, BigDecimal amount, 
                           DeductionType type, String reason, User approvedBy) {
        this.id = id;
        this.worker = worker;
        this.deductionDate = deductionDate;
        this.amount = amount;
        this.type = type;
        this.reason = reason;
        this.approvedBy = approvedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
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

    public DeductionType getType() {
        return type;
    }

    public void setType(DeductionType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public enum DeductionType {
        LATE_ARRIVAL, ABSENCE, DISCIPLINARY, TAX, INSURANCE, OTHER
    }
}
