package ac.cursor.umurimohub.worker.service;

import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional(transactionManager = "workerTransactionManager")
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
    }

    public Worker getWorkerByUserId(Long userId) {
        return workerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Worker not found for user"));
    }

    @Transactional(transactionManager = "workerTransactionManager")
    public Worker updateWorker(Worker worker) {
        return workerRepository.save(worker);
    }
}
