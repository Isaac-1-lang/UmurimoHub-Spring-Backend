package ac.cursor.umurimohub.attendance.dto;

import ac.cursor.umurimohub.attendance.model.Attendance;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRequest {
    @NotNull(message = "Worker ID is required")
    private Long workerId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Check-in time is required")
    private LocalTime checkIn;

    private LocalTime checkOut;

    @NotNull(message = "Status is required")
    private Attendance.AttendanceStatus status;

    private String notes;

    public AttendanceRequest() {
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public Attendance.AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(Attendance.AttendanceStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
