package ac.cursor.umurimohub.payroll.controller;

import ac.cursor.umurimohub.auth.port.CurrentUserPort;
import ac.cursor.umurimohub.auth.port.UserLookupPort;
import ac.cursor.umurimohub.payroll.dto.SalaryDeductionRequest;
import ac.cursor.umurimohub.payroll.dto.SalaryDeductionResponse;
import ac.cursor.umurimohub.payroll.model.SalaryDeduction;
import ac.cursor.umurimohub.payroll.service.SalaryDeductionService;
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

import java.util.List;

@RestController
@RequestMapping("/api/salary-deductions")
@Tag(name = "Salary Deductions", description = "Salary deduction management APIs")
@SecurityRequirement(name = "bearerAuth")
public class SalaryDeductionRestController {

    private final SalaryDeductionService salaryDeductionService;
    private final WorkerLookupPort workerLookupPort;
    private final CurrentUserPort currentUserPort;
    private final UserLookupPort userLookupPort;

    public SalaryDeductionRestController(SalaryDeductionService salaryDeductionService,
                                         WorkerLookupPort workerLookupPort,
                                         CurrentUserPort currentUserPort,
                                         UserLookupPort userLookupPort) {
        this.salaryDeductionService = salaryDeductionService;
        this.workerLookupPort = workerLookupPort;
        this.currentUserPort = currentUserPort;
        this.userLookupPort = userLookupPort;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all salary deductions", description = "Retrieve all salary deductions (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<SalaryDeductionResponse>>> getAllDeductions() {
        List<SalaryDeductionResponse> deductions = salaryDeductionService.getAllDeductions().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Salary deductions retrieved", deductions));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker salary deductions", description = "Get salary deductions for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<SalaryDeductionResponse>>> getWorkerDeductions(@PathVariable Long workerId) {
        workerLookupPort.assertExists(workerId);
        List<SalaryDeductionResponse> deductions = salaryDeductionService.getWorkerDeductions(workerId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Worker salary deductions retrieved", deductions));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create salary deduction", description = "Create a new salary deduction (HR only)")
    public ResponseEntity<ApiResponse<SalaryDeductionResponse>> createDeduction(@Valid @RequestBody SalaryDeductionRequest request) {
        workerLookupPort.assertExists(request.getWorkerId());
        var currentUser = currentUserPort.getCurrentUser();

        SalaryDeduction deduction = new SalaryDeduction();
        deduction.setWorkerId(request.getWorkerId());
        deduction.setDeductionDate(request.getDeductionDate());
        deduction.setAmount(request.getAmount());
        deduction.setType(request.getType());
        deduction.setReason(request.getReason());
        deduction.setApprovedByUserId(currentUser.id());

        SalaryDeduction savedDeduction = salaryDeductionService.createDeduction(deduction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Salary deduction created successfully", toResponse(savedDeduction)));
    }

    @GetMapping("/my-deductions")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my salary deductions", description = "Get current worker's salary deductions (Workers only)")
    public ResponseEntity<ApiResponse<List<SalaryDeductionResponse>>> getMyDeductions() {
        var worker = workerLookupPort.requireByUserId(currentUserPort.getCurrentUser().id());
        List<SalaryDeductionResponse> deductions = salaryDeductionService.getWorkerDeductions(worker.id()).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Your salary deductions retrieved", deductions));
    }

    private SalaryDeductionResponse toResponse(SalaryDeduction deduction) {
        var worker = workerLookupPort.requireById(deduction.getWorkerId());
        var approvedBy = deduction.getApprovedByUserId() != null
                ? userLookupPort.requireById(deduction.getApprovedByUserId())
                : null;
        return SalaryDeductionResponse.from(deduction, worker, approvedBy);
    }
}
