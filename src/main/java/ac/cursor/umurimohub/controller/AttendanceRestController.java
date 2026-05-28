package ac.cursor.umurimohub.controller;

import ac.cursor.umurimohub.dto.ApiResponse;
import ac.cursor.umurimohub.dto.AttendanceRequest;
import ac.cursor.umurimohub.model.Attendance;
import ac.cursor.umurimohub.model.User;
import ac.cursor.umurimohub.model.Worker;
import ac.cursor.umurimohub.service.AttendanceService;
import ac.cursor.umurimohub.service.UserService;
import ac.cursor.umurimohub.service.WorkerService;
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
    private final WorkerService workerService;
    private final UserService userService;

    public AttendanceRestController(AttendanceService attendanceService,
                                    WorkerService workerService, UserService userService) {
        this.attendanceService = attendanceService;
        this.workerService = workerService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all attendance records", description = "Retrieve all attendance records (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAllAttendance() {
        List<Attendance> attendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(ApiResponse.success("Attendance records retrieved", attendance));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker attendance", description = "Get attendance records for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<Attendance>>> getWorkerAttendance(@PathVariable Long workerId) {
        Worker worker = workerService.getWorkerById(workerId);
        List<Attendance> attendance = attendanceService.getWorkerAttendance(worker);
        return ResponseEntity.ok(ApiResponse.success("Worker attendance retrieved", attendance));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get attendance by date range", description = "Get attendance records within date range (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendanceByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendance = attendanceService.getAttendanceByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Attendance records retrieved", attendance));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Record attendance", description = "Record attendance for a worker (HR only)")
    public ResponseEntity<ApiResponse<Attendance>> recordAttendance(@Valid @RequestBody AttendanceRequest request) {
        Worker worker = workerService.getWorkerById(request.getWorkerId());

        Attendance attendance = new Attendance();
        attendance.setWorker(worker);
        attendance.setDate(request.getDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());
        attendance.setStatus(request.getStatus());
        attendance.setNotes(request.getNotes());

        Attendance savedAttendance = attendanceService.recordAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attendance recorded successfully", savedAttendance));
    }

    @GetMapping("/my-attendance")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my attendance", description = "Get current worker's attendance records (Workers only)")
    public ResponseEntity<ApiResponse<List<Attendance>>> getMyAttendance() {
        User currentUser = userService.getCurrentUser();
        Worker worker = workerService.getWorkerByUserId(currentUser.getId());
        List<Attendance> attendance = attendanceService.getWorkerAttendance(worker);
        return ResponseEntity.ok(ApiResponse.success("Your attendance retrieved", attendance));
    }
}
