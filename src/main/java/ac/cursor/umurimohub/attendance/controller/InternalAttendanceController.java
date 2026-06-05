package ac.cursor.umurimohub.attendance.controller;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.port.AttendanceQueryPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/attendance")
@Profile("service-attendance")
public class InternalAttendanceController {

    private final AttendanceQueryPort attendanceQueryPort;

    public InternalAttendanceController(AttendanceQueryPort attendanceQueryPort) {
        this.attendanceQueryPort = attendanceQueryPort;
    }

    @GetMapping
    public ApiResponse<List<Attendance>> getAll() {
        return ApiResponse.success("Attendance", attendanceQueryPort.getAllAttendance());
    }
}
