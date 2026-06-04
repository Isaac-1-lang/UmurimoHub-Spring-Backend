package ac.cursor.umurimohub.discipline.controller;

import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.auth.port.UserLookupPort;
import ac.cursor.umurimohub.discipline.dto.DisciplinaryActionRequest;
import ac.cursor.umurimohub.discipline.dto.DisciplinaryActionResponse;
import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.discipline.service.DisciplinaryActionService;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
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
    private final WorkerLookupPort workerLookupPort;
    private final CurrentUserPort currentUserPort;
    private final UserLookupPort userLookupPort;

    public DisciplinaryActionRestController(DisciplinaryActionService disciplinaryActionService,
                                            WorkerLookupPort workerLookupPort,
                                            CurrentUserPort currentUserPort,
                                            UserLookupPort userLookupPort) {
        this.disciplinaryActionService = disciplinaryActionService;
        this.workerLookupPort = workerLookupPort;
        this.currentUserPort = currentUserPort;
        this.userLookupPort = userLookupPort;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all disciplinary actions", description = "Retrieve all disciplinary actions (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryActionResponse>>> getAllActions() {
        List<DisciplinaryActionResponse> actions = disciplinaryActionService.getAllActions().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Disciplinary actions retrieved", actions));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker disciplinary actions", description = "Get disciplinary actions for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryActionResponse>>> getWorkerActions(@PathVariable Long workerId) {
        workerLookupPort.assertExists(workerId);
        List<DisciplinaryActionResponse> actions = disciplinaryActionService.getWorkerActions(workerId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Worker disciplinary actions retrieved", actions));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create disciplinary action", description = "Create a new disciplinary action (HR only)")
    public ResponseEntity<ApiResponse<DisciplinaryActionResponse>> createAction(@Valid @RequestBody DisciplinaryActionRequest request) {
        workerLookupPort.assertExists(request.getWorkerId());
        var currentUser = currentUserPort.getCurrentUser();

        DisciplinaryAction action = new DisciplinaryAction();
        action.setWorkerId(request.getWorkerId());
        action.setActionDate(LocalDate.now());
        action.setType(request.getType());
        action.setDescription(request.getDescription());
        action.setSeverity(request.getSeverity());
        action.setIssuedByUserId(currentUser.id());

        DisciplinaryAction savedAction = disciplinaryActionService.createAction(action);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Disciplinary action created successfully", toResponse(savedAction)));
    }

    @GetMapping("/my-actions")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my disciplinary actions", description = "Get current worker's disciplinary actions (Workers only)")
    public ResponseEntity<ApiResponse<List<DisciplinaryActionResponse>>> getMyActions() {
        var worker = workerLookupPort.requireByUserId(currentUserPort.getCurrentUser().id());
        List<DisciplinaryActionResponse> actions = disciplinaryActionService.getWorkerActions(worker.id()).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Your disciplinary actions retrieved", actions));
    }

    private DisciplinaryActionResponse toResponse(DisciplinaryAction action) {
        return DisciplinaryActionResponse.from(
                action,
                workerLookupPort.requireById(action.getWorkerId()),
                userLookupPort.requireById(action.getIssuedByUserId()));
    }
}
