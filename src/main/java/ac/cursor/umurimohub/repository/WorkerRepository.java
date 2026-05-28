package ac.cursor.umurimohub.repository;

import ac.cursor.umurimohub.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByEmployeeId(String employeeId);
    Optional<Worker> findByUserId(Long userId);
}
