package ac.cursor.umurimohub.config.seed;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.repository.AttendanceRepository;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Order(3)
public class AttendanceSampleDataLoader implements CommandLineRunner {

    private final WorkerRepository workerRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceSampleDataLoader(WorkerRepository workerRepository, AttendanceRepository attendanceRepository) {
        this.workerRepository = workerRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    @Transactional(transactionManager = "attendanceTransactionManager")
    public void run(String... args) {
        if (attendanceRepository.count() > 0) {
            return;
        }

        var worker1 = workerRepository.findByEmployeeId("EMP001").orElseThrow();

        Attendance att1 = new Attendance();
        att1.setWorkerId(worker1.getId());
        att1.setDate(LocalDate.now());
        att1.setCheckIn(LocalTime.of(8, 0));
        att1.setCheckOut(LocalTime.of(17, 0));
        att1.setStatus(Attendance.AttendanceStatus.PRESENT);
        attendanceRepository.save(att1);

        System.out.println("=== Sample credentials ===");
        System.out.println("CEO - username: ceo, password: ceo123");
        System.out.println("HR - username: hr, password: hr123");
        System.out.println("Worker1 - username: worker1, password: worker123");
        System.out.println("Worker2 - username: worker2, password: worker123");
    }
}
