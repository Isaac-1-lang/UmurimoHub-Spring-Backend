package ac.cursor.umurimohub.dto;

import ac.cursor.umurimohub.model.DisciplinaryAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DisciplinaryActionRequest {
    @NotNull(message = "Worker ID is required")
    private Long workerId;

    @NotNull(message = "Action type is required")
    private DisciplinaryAction.ActionType type;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Severity is required")
    private DisciplinaryAction.Severity severity;

    public DisciplinaryActionRequest() {
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public DisciplinaryAction.ActionType getType() {
        return type;
    }

    public void setType(DisciplinaryAction.ActionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DisciplinaryAction.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(DisciplinaryAction.Severity severity) {
        this.severity = severity;
    }
}
