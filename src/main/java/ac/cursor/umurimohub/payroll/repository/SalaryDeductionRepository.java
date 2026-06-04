package ac.cursor.umurimohub.payroll.repository;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.worker.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDeductionRepository extends JpaRepository<SalaryDeduction, Long> {
    List<SalaryDeduction> findByWorkerOrderByDeductionDateDesc(Worker worker);
}
