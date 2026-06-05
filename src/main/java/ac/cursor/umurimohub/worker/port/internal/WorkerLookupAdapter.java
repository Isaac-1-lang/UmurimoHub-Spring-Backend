package ac.cursor.umurimohub.worker.port.internal;

import ac.cursor.umurimohub.auth.port.UserLookupPort;
import ac.cursor.umurimohub.auth.port.dto.UserRef;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class WorkerLookupAdapter implements WorkerLookupPort {

    private final WorkerRepository workerRepository;
    private final UserLookupPort userLookupPort;

    public WorkerLookupAdapter(WorkerRepository workerRepository, UserLookupPort userLookupPort) {
        this.workerRepository = workerRepository;
        this.userLookupPort = userLookupPort;
    }

    @Override
    public Optional<WorkerRef> findById(Long id) {
        return workerRepository.findById(id).map(this::toRef);
    }

    @Override
    public Optional<WorkerRef> findByUserId(Long userId) {
        return workerRepository.findByUserId(userId).map(this::toRef);
    }

    @Override
    public WorkerRef requireById(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("Worker not found"));
    }

    @Override
    public WorkerRef requireByUserId(Long userId) {
        return findByUserId(userId).orElseThrow(() -> new RuntimeException("Worker not found for user"));
    }

    @Override
    public List<WorkerRef> findAll() {
        return workerRepository.findAll().stream().map(this::toRef).toList();
    }

    @Override
    public long countWorkers() {
        return workerRepository.count();
    }

    @Override
    public void assertExists(Long workerId) {
        if (!workerRepository.existsById(workerId)) {
            throw new RuntimeException("Worker not found");
        }
    }

    private WorkerRef toRef(Worker worker) {
        UserRef user = userLookupPort.requireById(worker.getUserId());
        return new WorkerRef(
                worker.getId(),
                worker.getUserId(),
                worker.getEmployeeId(),
                worker.getDepartment(),
                worker.getPosition(),
                worker.getHireDate(),
                worker.getBaseSalary(),
                worker.getStatus().name(),
                user
        );
    }
}
