package ac.cursor.umurimohub.worker.controller;

import ac.cursor.umurimohub.auth.port.UserProvisioningPort;
import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.auth.port.dto.CreateUserCommand;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.worker.dto.WorkerRequest;
import ac.cursor.umurimohub.worker.dto.WorkerResponse;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import ac.cursor.umurimohub.worker.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@Tag(name = "Workers", description = "Worker management APIs")
@SecurityRequirement(name = "bearerAuth")
public class WorkerRestController {

    private final WorkerService workerService;
    private final WorkerLookupPort workerLookupPort;
    private final UserProvisioningPort userProvisioningPort;
    private final CurrentUserPort currentUserPort;

    public WorkerRestController(WorkerService workerService,
                                WorkerLookupPort workerLookupPort,
                                UserProvisioningPort userProvisioningPort,
                                CurrentUserPort currentUserPort) {
        this.workerService = workerService;
        this.workerLookupPort = workerLookupPort;
        this.userProvisioningPort = userProvisioningPort;
        this.currentUserPort = currentUserPort;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all workers", description = "Retrieve list of all workers (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<WorkerResponse>>> getAllWorkers() {
        List<WorkerResponse> workers = workerLookupPort.findAll().stream()
                .map(WorkerResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Workers retrieved successfully", workers));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker by ID", description = "Retrieve worker details by ID (HR and CEO only)")
    public ResponseEntity<ApiResponse<WorkerResponse>> getWorkerById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Worker retrieved successfully",
                WorkerResponse.from(workerLookupPort.requireById(id))));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create new worker", description = "Create a new worker with user account (HR only)")
    public ResponseEntity<ApiResponse<WorkerResponse>> createWorker(@Valid @RequestBody WorkerRequest request) {
        var userRef = userProvisioningPort.createUserWithCredentials(new CreateUserCommand(
                request.getUsername(),
                request.getPassword(),
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                "WORKER"
        ));

        Worker worker = new Worker();
        worker.setUserId(userRef.id());
        worker.setEmployeeId(request.getEmployeeId());
        worker.setDepartment(request.getDepartment());
        worker.setPosition(request.getPosition());
        worker.setHireDate(request.getHireDate());
        worker.setBaseSalary(request.getBaseSalary());

        Worker savedWorker = workerService.createWorker(worker);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Worker created successfully",
                        WorkerResponse.from(workerLookupPort.requireById(savedWorker.getId()))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Update worker", description = "Update worker details (HR only)")
    public ResponseEntity<ApiResponse<WorkerResponse>> updateWorker(
            @PathVariable Long id,
            @Valid @RequestBody WorkerRequest request) {
        Worker worker = workerService.getWorkerById(id);
        worker.setEmployeeId(request.getEmployeeId());
        worker.setDepartment(request.getDepartment());
        worker.setPosition(request.getPosition());
        worker.setHireDate(request.getHireDate());
        worker.setBaseSalary(request.getBaseSalary());

        workerService.updateWorker(worker);
        return ResponseEntity.ok(ApiResponse.success(
                "Worker updated successfully",
                WorkerResponse.from(workerLookupPort.requireById(id))));
    }

    @GetMapping("/my-profile")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my worker profile", description = "Get current worker's profile (Workers only)")
    public ResponseEntity<ApiResponse<WorkerResponse>> getMyProfile() {
        var currentUser = currentUserPort.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(
                "Profile retrieved successfully",
                WorkerResponse.from(workerLookupPort.requireByUserId(currentUser.id()))));
    }
}
