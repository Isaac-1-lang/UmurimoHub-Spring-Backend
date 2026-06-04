package ac.cursor.umurimohub.payroll.service;

import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.payroll.repository.SalaryDeductionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaryDeductionService {

    private final SalaryDeductionRepository deductionRepository;

    public SalaryDeductionService(SalaryDeductionRepository deductionRepository) {
        this.deductionRepository = deductionRepository;
    }

    @Transactional
    public SalaryDeduction createDeduction(SalaryDeduction deduction) {
        return deductionRepository.save(deduction);
    }

    public List<SalaryDeduction> getWorkerDeductions(Worker worker) {
        return deductionRepository.findByWorkerOrderByDeductionDateDesc(worker);
    }

    public List<SalaryDeduction> getAllDeductions() {
        return deductionRepository.findAll();
    }
}
