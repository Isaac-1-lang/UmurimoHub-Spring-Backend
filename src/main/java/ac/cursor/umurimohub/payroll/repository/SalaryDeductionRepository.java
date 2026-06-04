package ac.cursor.umurimohub.payroll.repository;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDeductionRepository extends JpaRepository<SalaryDeduction, Long> {
    List<SalaryDeduction> findByWorkerIdOrderByDeductionDateDesc(Long workerId);
}
