package ac.cursor.umurimohub.attendance.port;

import ac.cursor.umurimohub.attendance.model.Attendance;

import java.util.List;

public interface AttendanceQueryPort {
    List<Attendance> getAllAttendance();
}
