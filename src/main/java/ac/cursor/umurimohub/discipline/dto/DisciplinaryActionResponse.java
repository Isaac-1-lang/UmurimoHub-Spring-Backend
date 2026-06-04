package ac.cursor.umurimohub.discipline.dto;

import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;

import java.time.LocalDate;

public record DisciplinaryActionResponse(
        Long id,
        LocalDate actionDate,
        DisciplinaryAction.ActionType type,
        String description,
        DisciplinaryAction.Severity severity,
        WorkerRef worker,
        UserRef issuedBy
) {
    public static DisciplinaryActionResponse from(
            DisciplinaryAction action,
            WorkerRef worker,
            UserRef issuedBy) {
        return new DisciplinaryActionResponse(
                action.getId(),
                action.getActionDate(),
                action.getType(),
                action.getDescription(),
                action.getSeverity(),
                worker,
                issuedBy
        );
    }
}
