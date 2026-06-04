package ac.cursor.umurimohub.attendance.repository;

import ac.cursor.umurimohub.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByWorkerIdOrderByDateDesc(Long workerId);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<Attendance> findByWorkerIdAndDate(Long workerId, LocalDate date);
}
