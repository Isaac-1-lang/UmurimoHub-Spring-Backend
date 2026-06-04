package ac.cursor.umurimohub.worker.port.dto;

import ac.cursor.umurimohub.auth.port.dto.UserRef;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WorkerRef(
        Long id,
        Long userId,
        String employeeId,
        String department,
        String position,
        LocalDate hireDate,
        BigDecimal baseSalary,
        String status,
        UserRef user
) {
}
