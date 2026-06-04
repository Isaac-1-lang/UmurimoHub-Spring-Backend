package ac.cursor.umurimohub.worker.dto;

import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * API shape compatible with nested {@code user} JSON expected by the frontend.
 */
public record WorkerResponse(
        Long id,
        String employeeId,
        String department,
        String position,
        LocalDate hireDate,
        BigDecimal baseSalary,
        String status,
        UserRef user
) {
    public static WorkerResponse from(WorkerRef ref) {
        return new WorkerResponse(
                ref.id(),
                ref.employeeId(),
                ref.department(),
                ref.position(),
                ref.hireDate(),
                ref.baseSalary(),
                ref.status(),
                ref.user()
        );
    }

    public static WorkerResponse from(Worker worker, UserRef user) {
        return new WorkerResponse(
                worker.getId(),
                worker.getEmployeeId(),
                worker.getDepartment(),
                worker.getPosition(),
                worker.getHireDate(),
                worker.getBaseSalary(),
                worker.getStatus().name(),
                user
        );
    }
}
