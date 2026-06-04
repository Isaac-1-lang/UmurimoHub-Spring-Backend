package ac.cursor.umurimohub.attendance.controller;

import ac.cursor.umurimohub.attendance.dto.AttendanceRequest;
import ac.cursor.umurimohub.attendance.dto.AttendanceResponse;
import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.service.AttendanceService;
import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance", description = "Attendance management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AttendanceRestController {

    private final AttendanceService attendanceService;
    private final WorkerLookupPort workerLookupPort;
    private final CurrentUserPort currentUserPort;

    public AttendanceRestController(AttendanceService attendanceService,
                                    WorkerLookupPort workerLookupPort,
                                    CurrentUserPort currentUserPort) {
        this.attendanceService = attendanceService;
        this.workerLookupPort = workerLookupPort;
        this.currentUserPort = currentUserPort;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all attendance records", description = "Retrieve all attendance records (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendance() {
        List<AttendanceResponse> attendance = attendanceService.getAllAttendance().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Attendance records retrieved", attendance));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker attendance", description = "Get attendance records for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getWorkerAttendance(@PathVariable Long workerId) {
        workerLookupPort.assertExists(workerId);
        List<AttendanceResponse> attendance = attendanceService.getWorkerAttendance(workerId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Worker attendance retrieved", attendance));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get attendance by date range", description = "Get attendance records within date range (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceResponse> attendance = attendanceService.getAttendanceByDateRange(startDate, endDate).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Attendance records retrieved", attendance));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Record attendance", description = "Record attendance for a worker (HR only)")
    public ResponseEntity<ApiResponse<AttendanceResponse>> recordAttendance(@Valid @RequestBody AttendanceRequest request) {
        workerLookupPort.assertExists(request.getWorkerId());

        Attendance attendance = new Attendance();
        attendance.setWorkerId(request.getWorkerId());
        attendance.setDate(request.getDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());
        attendance.setStatus(request.getStatus());
        attendance.setNotes(request.getNotes());

        Attendance savedAttendance = attendanceService.recordAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attendance recorded successfully", toResponse(savedAttendance)));
    }

    @GetMapping("/my-attendance")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my attendance", description = "Get current worker's attendance records (Workers only)")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getMyAttendance() {
        var worker = workerLookupPort.requireByUserId(currentUserPort.getCurrentUser().id());
        List<AttendanceResponse> attendance = attendanceService.getWorkerAttendance(worker.id()).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Your attendance retrieved", attendance));
    }

    private AttendanceResponse toResponse(Attendance attendance) {
        return AttendanceResponse.from(
                attendance,
                workerLookupPort.requireById(attendance.getWorkerId()));
    }
}
