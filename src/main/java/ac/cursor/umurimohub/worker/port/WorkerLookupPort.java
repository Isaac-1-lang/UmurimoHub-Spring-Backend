package ac.cursor.umurimohub.worker.port;

import ac.cursor.umurimohub.worker.port.dto.WorkerRef;

import java.util.List;
import java.util.Optional;

public interface WorkerLookupPort {

    Optional<WorkerRef> findById(Long id);

    Optional<WorkerRef> findByUserId(Long userId);

    WorkerRef requireById(Long id);

    WorkerRef requireByUserId(Long userId);

    List<WorkerRef> findAll();

    long countWorkers();

    void assertExists(Long workerId);
}
