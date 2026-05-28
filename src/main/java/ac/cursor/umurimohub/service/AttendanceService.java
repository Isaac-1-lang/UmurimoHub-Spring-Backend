package ac.cursor.umurimohub.service;

import ac.cursor.umurimohub.model.Attendance;
import ac.cursor.umurimohub.model.Worker;
import ac.cursor.umurimohub.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public Attendance recordAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getWorkerAttendance(Worker worker) {
        return attendanceRepository.findByWorkerOrderByDateDesc(worker);
    }

    public List<Attendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}
