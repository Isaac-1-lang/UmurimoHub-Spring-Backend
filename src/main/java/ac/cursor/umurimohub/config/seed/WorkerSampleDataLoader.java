package ac.cursor.umurimohub.config.seed;

import ac.cursor.umurimohub.auth.repository.UserRepository;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Order(2)
public class WorkerSampleDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkerRepository workerRepository;

    public WorkerSampleDataLoader(UserRepository userRepository, WorkerRepository workerRepository) {
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    @Transactional(transactionManager = "workerTransactionManager")
    public void run(String... args) {
        if (workerRepository.count() > 0) {
            return;
        }

        var workerUser1 = userRepository.findByUsername("worker1").orElseThrow();
        var workerUser2 = userRepository.findByUsername("worker2").orElseThrow();

        Worker worker1 = new Worker();
        worker1.setUserId(workerUser1.getId());
        worker1.setEmployeeId("EMP001");
        worker1.setDepartment("IT");
        worker1.setPosition("Software Developer");
        worker1.setHireDate(LocalDate.of(2023, 1, 15));
        worker1.setBaseSalary(new BigDecimal("500000"));
        workerRepository.save(worker1);

        Worker worker2 = new Worker();
        worker2.setUserId(workerUser2.getId());
        worker2.setEmployeeId("EMP002");
        worker2.setDepartment("Sales");
        worker2.setPosition("Sales Representative");
        worker2.setHireDate(LocalDate.of(2023, 3, 20));
        worker2.setBaseSalary(new BigDecimal("400000"));
        workerRepository.save(worker2);

        System.out.println("=== Worker module sample data ready ===");
    }
}
