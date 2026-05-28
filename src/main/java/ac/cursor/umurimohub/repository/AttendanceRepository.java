package ac.cursor.umurimohub.repository;

import ac.cursor.umurimohub.model.Attendance;
import ac.cursor.umurimohub.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByWorkerOrderByDateDesc(Worker worker);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<Attendance> findByWorkerAndDate(Worker worker, LocalDate date);
}
