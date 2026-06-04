package ac.cursor.umurimohub.attendance.dto;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        Long id,
        LocalDate date,
        LocalTime checkIn,
        LocalTime checkOut,
        Attendance.AttendanceStatus status,
        String notes,
        WorkerRef worker
) {
    public static AttendanceResponse from(Attendance attendance, WorkerRef worker) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getDate(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getStatus(),
                attendance.getNotes(),
                worker
        );
    }
}
