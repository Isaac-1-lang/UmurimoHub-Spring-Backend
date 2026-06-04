package ac.cursor.umurimohub.discipline.controller;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.discipline.dto.DisciplinaryActionRequest;
import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.discipline.service.DisciplinaryActionService;
import ac.cursor.umurimohub.auth.service.UserService;
import ac.cursor.umurimohub.worker.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/disciplinary-actions")
@Tag(name = "Disciplinary Actions", description = "Disciplinary action management APIs")
@SecurityRequirement(name = "bearerAuth")
public class DisciplinaryActionRestController {

    private final DisciplinaryActionService disciplinaryActionService;
    private final WorkerService workerService;
    private final UserService userService;

    public DisciplinaryActionRestController(DisciplinaryActionService disciplinaryActionService,
                                            WorkerService workerService, UserService userService) {
        this.disciplinaryActionService = disciplinaryActionService;
        this.workerService = workerService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all disciplinary actions", description = "Retrieve all disciplinary actions (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryAction>>> getAllActions() {
        List<DisciplinaryAction> actions = disciplinaryActionService.getAllActions();
        return ResponseEntity.ok(ApiResponse.success("Disciplinary actions retrieved", actions));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker disciplinary actions", description = "Get disciplinary actions for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryAction>>> getWorkerActions(@PathVariable Long workerId) {
        Worker worker = workerService.getWorkerById(workerId);
        List<DisciplinaryAction> actions = disciplinaryActionService.getWorkerActions(worker);
        return ResponseEntity.ok(ApiResponse.success("Worker disciplinary actions retrieved", actions));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create disciplinary action", description = "Create a new disciplinary action (HR only)")
    public ResponseEntity<ApiResponse<DisciplinaryAction>> createAction(@Valid @RequestBody DisciplinaryActionRequest request) {
        Worker worker = workerService.getWorkerById(request.getWorkerId());
        User currentUser = userService.getCurrentUser();

        DisciplinaryAction action = new DisciplinaryAction();
        action.setWorker(worker);
        action.setActionDate(LocalDate.now());
        action.setType(request.getType());
        action.setDescription(request.getDescription());
        action.setSeverity(request.getSeverity());
        action.setIssuedBy(currentUser);

        DisciplinaryAction savedAction = disciplinaryActionService.createAction(action);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Disciplinary action created successfully", savedAction));
    }

    @GetMapping("/my-actions")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my disciplinary actions", description = "Get current worker's disciplinary actions (Workers only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryAction>>> getMyActions() {
        User currentUser = userService.getCurrentUser();
        Worker worker = workerService.getWorkerByUserId(currentUser.getId());
        List<DisciplinaryAction> actions = disciplinaryActionService.getWorkerActions(worker);
        return ResponseEntity.ok(ApiResponse.success("Your disciplinary actions retrieved", actions));
    }
}
