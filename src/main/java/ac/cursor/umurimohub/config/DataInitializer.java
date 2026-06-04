package ac.cursor.umurimohub.config;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.repository.AttendanceRepository;
import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.auth.repository.UserRepository;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, WorkerRepository workerRepository,
                           AttendanceRepository attendanceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.attendanceRepository = attendanceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            // Create CEO
            User ceo = new User();
            ceo.setUsername("ceo");
            ceo.setPassword(passwordEncoder.encode("ceo123"));
            ceo.setFullName("example CEO");
            ceo.setEmail("ceo@umurimohub.ac");
            ceo.setPhoneNumber("+250788000001");
            ceo.setRole(User.Role.CEO);
            userRepository.save(ceo);

            // Create HR
            User hr = new User();
            hr.setUsername("hr");
            hr.setPassword(passwordEncoder.encode("hr123"));
            hr.setFullName("Jane HR Manager");
            hr.setEmail("hr@umurimohub.ac");
            hr.setPhoneNumber("+250788000002");
            hr.setRole(User.Role.HR);
            userRepository.save(hr);

            // Create Worker 1
            User workerUser1 = new User();
            workerUser1.setUsername("worker1");
            workerUser1.setPassword(passwordEncoder.encode("worker123"));
            workerUser1.setFullName("Alice Worker");
            workerUser1.setEmail("alice@umurimohub.ac");
            workerUser1.setPhoneNumber("+250788000003");
            workerUser1.setRole(User.Role.WORKER);
            userRepository.save(workerUser1);

            Worker worker1 = new Worker();
            worker1.setUser(workerUser1);
            worker1.setEmployeeId("EMP001");
            worker1.setDepartment("IT");
            worker1.setPosition("Software Developer");
            worker1.setHireDate(LocalDate.of(2023, 1, 15));
            worker1.setBaseSalary(new BigDecimal("500000"));
            workerRepository.save(worker1);

            // Create Worker 2
            User workerUser2 = new User();
            workerUser2.setUsername("worker2");
            workerUser2.setPassword(passwordEncoder.encode("worker123"));
            workerUser2.setFullName("Bob Worker");
            workerUser2.setEmail("bob@umurimohub.ac");
            workerUser2.setPhoneNumber("+250788000004");
            workerUser2.setRole(User.Role.WORKER);
            userRepository.save(workerUser2);

            Worker worker2 = new Worker();
            worker2.setUser(workerUser2);
            worker2.setEmployeeId("EMP002");
            worker2.setDepartment("Sales");
            worker2.setPosition("Sales Representative");
            worker2.setHireDate(LocalDate.of(2023, 3, 20));
            worker2.setBaseSalary(new BigDecimal("400000"));
            workerRepository.save(worker2);

            // Sample attendance
            Attendance att1 = new Attendance();
            att1.setWorker(worker1);
            att1.setDate(LocalDate.now());
            att1.setCheckIn(LocalTime.of(8, 0));
            att1.setCheckOut(LocalTime.of(17, 0));
            att1.setStatus(Attendance.AttendanceStatus.PRESENT);
            attendanceRepository.save(att1);

            System.out.println("=== Sample Data Initialized ===");
            System.out.println("CEO - username: ceo, password: ceo123");
            System.out.println("HR - username: hr, password: hr123");
            System.out.println("Worker1 - username: worker1, password: worker123");
            System.out.println("Worker2 - username: worker2, password: worker123");
        }
    }
}
