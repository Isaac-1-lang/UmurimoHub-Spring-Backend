package ac.cursor.umurimohub.worker.controller;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import ac.cursor.umurimohub.worker.port.dto.WorkerRef;
import ac.cursor.umurimohub.worker.repository.WorkerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/internal/workers")
@Profile("service-worker")
public class InternalWorkerController {

    private final WorkerLookupPort workerLookupPort;
    private final WorkerRepository workerRepository;

    public InternalWorkerController(WorkerLookupPort workerLookupPort, WorkerRepository workerRepository) {
        this.workerLookupPort = workerLookupPort;
        this.workerRepository = workerRepository;
    }

    @GetMapping
    public ApiResponse<List<WorkerRef>> getAll() {
        return ApiResponse.success("Workers", workerLookupPort.findAll());
    }

    @GetMapping("/count")
    public ApiResponse<Long> count() {
        return ApiResponse.success("Count", workerLookupPort.countWorkers());
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkerRef> getById(@PathVariable Long id) {
        return ApiResponse.success("Worker", workerLookupPort.requireById(id));
    }

    @GetMapping("/by-user/{userId}")
    public ApiResponse<WorkerRef> getByUserId(@PathVariable Long userId) {
        return ApiResponse.success("Worker", workerLookupPort.requireByUserId(userId));
    }

    @GetMapping("/{id}/exists")
    public void exists(@PathVariable Long id) {
        if (!workerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found");
        }
    }
}
