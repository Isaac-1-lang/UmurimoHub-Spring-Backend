package ac.cursor.umurimohub.repository;

import ac.cursor.umurimohub.model.SalaryDeduction;
import ac.cursor.umurimohub.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDeductionRepository extends JpaRepository<SalaryDeduction, Long> {
    List<SalaryDeduction> findByWorkerOrderByDeductionDateDesc(Worker worker);
}
