package ac.cursor.umurimohub.controller;

import ac.cursor.umurimohub.dto.ApiResponse;
import ac.cursor.umurimohub.dto.WorkerRequest;
import ac.cursor.umurimohub.model.User;
import ac.cursor.umurimohub.model.Worker;
import ac.cursor.umurimohub.service.UserService;
import ac.cursor.umurimohub.service.WorkerService;
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
    private final UserService userService;

    public WorkerRestController(WorkerService workerService, UserService userService) {
        this.workerService = workerService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all workers", description = "Retrieve list of all workers (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<Worker>>> getAllWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        return ResponseEntity.ok(ApiResponse.success("Workers retrieved successfully", workers));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker by ID", description = "Retrieve worker details by ID (HR and CEO only)")
    public ResponseEntity<ApiResponse<Worker>> getWorkerById(@PathVariable Long id) {
        Worker worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(ApiResponse.success("Worker retrieved successfully", worker));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create new worker", description = "Create a new worker with user account (HR only)")
    public ResponseEntity<ApiResponse<Worker>> createWorker(@Valid @RequestBody WorkerRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(User.Role.WORKER);

        User savedUser = userService.createUserWithEmailNotification(user);

        Worker worker = new Worker();
        worker.setUser(savedUser);
        worker.setEmployeeId(request.getEmployeeId());
        worker.setDepartment(request.getDepartment());
        worker.setPosition(request.getPosition());
        worker.setHireDate(request.getHireDate());
        worker.setBaseSalary(request.getBaseSalary());

        Worker savedWorker = workerService.createWorker(worker);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Worker created successfully", savedWorker));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Update worker", description = "Update worker details (HR only)")
    public ResponseEntity<ApiResponse<Worker>> updateWorker(
            @PathVariable Long id,
            @Valid @RequestBody WorkerRequest request) {
        Worker worker = workerService.getWorkerById(id);
        worker.setEmployeeId(request.getEmployeeId());
        worker.setDepartment(request.getDepartment());
        worker.setPosition(request.getPosition());
        worker.setHireDate(request.getHireDate());
        worker.setBaseSalary(request.getBaseSalary());

        Worker updatedWorker = workerService.updateWorker(worker);
        return ResponseEntity.ok(ApiResponse.success("Worker updated successfully", updatedWorker));
    }

    @GetMapping("/my-profile")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my worker profile", description = "Get current worker's profile (Workers only)")
    public ResponseEntity<ApiResponse<Worker>> getMyProfile() {
        User currentUser = userService.getCurrentUser();
        Worker worker = workerService.getWorkerByUserId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", worker));
    }
}
