package ac.cursor.umurimohub.attendance.port.internal;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.port.AttendanceQueryPort;
import ac.cursor.umurimohub.attendance.service.AttendanceService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!distributed & !service-attendance")
public class AttendanceQueryAdapter implements AttendanceQueryPort {

    private final AttendanceService attendanceService;

    public AttendanceQueryAdapter(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }
}
