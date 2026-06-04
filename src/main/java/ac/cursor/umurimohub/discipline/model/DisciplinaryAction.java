package ac.cursor.umurimohub.discipline.model;

import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.worker.model.Worker;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "disciplinary_actions")
public class DisciplinaryAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate actionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType type;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @ManyToOne
    @JoinColumn(name = "issued_by", nullable = false)
    private User issuedBy;

    public DisciplinaryAction() {
    }

    public DisciplinaryAction(Long id, Worker worker, LocalDate actionDate, ActionType type, 
                              String description, Severity severity, User issuedBy) {
        this.id = id;
        this.worker = worker;
        this.actionDate = actionDate;
        this.type = type;
        this.description = description;
        this.severity = severity;
        this.issuedBy = issuedBy;
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

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public User getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }

    public enum ActionType {
        WARNING, SUSPENSION, TERMINATION, WRITTEN_WARNING, VERBAL_WARNING
    }

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
