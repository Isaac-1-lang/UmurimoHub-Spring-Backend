package ac.cursor.umurimohub.controller;

import ac.cursor.umurimohub.dto.ApiResponse;
import ac.cursor.umurimohub.dto.SalaryDeductionRequest;
import ac.cursor.umurimohub.model.SalaryDeduction;
import ac.cursor.umurimohub.model.User;
import ac.cursor.umurimohub.model.Worker;
import ac.cursor.umurimohub.service.SalaryDeductionService;
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
@RequestMapping("/api/salary-deductions")
@Tag(name = "Salary Deductions", description = "Salary deduction management APIs")
@SecurityRequirement(name = "bearerAuth")
public class SalaryDeductionRestController {

    private final SalaryDeductionService salaryDeductionService;
    private final WorkerService workerService;
    private final UserService userService;

    public SalaryDeductionRestController(SalaryDeductionService salaryDeductionService,
                                         WorkerService workerService, UserService userService) {
        this.salaryDeductionService = salaryDeductionService;
        this.workerService = workerService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get all salary deductions", description = "Retrieve all salary deductions (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<SalaryDeduction>>> getAllDeductions() {
        List<SalaryDeduction> deductions = salaryDeductionService.getAllDeductions();
        return ResponseEntity.ok(ApiResponse.success("Salary deductions retrieved", deductions));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Get worker salary deductions", description = "Get salary deductions for specific worker (HR and CEO only)")
    public ResponseEntity<ApiResponse<List<SalaryDeduction>>> getWorkerDeductions(@PathVariable Long workerId) {
        Worker worker = workerService.getWorkerById(workerId);
        List<SalaryDeduction> deductions = salaryDeductionService.getWorkerDeductions(worker);
        return ResponseEntity.ok(ApiResponse.success("Worker salary deductions retrieved", deductions));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Create salary deduction", description = "Create a new salary deduction (HR only)")
    public ResponseEntity<ApiResponse<SalaryDeduction>> createDeduction(@Valid @RequestBody SalaryDeductionRequest request) {
        Worker worker = workerService.getWorkerById(request.getWorkerId());
        User currentUser = userService.getCurrentUser();

        SalaryDeduction deduction = new SalaryDeduction();
        deduction.setWorker(worker);
        deduction.setDeductionDate(request.getDeductionDate());
        deduction.setAmount(request.getAmount());
        deduction.setType(request.getType());
        deduction.setReason(request.getReason());
        deduction.setApprovedBy(currentUser);

        SalaryDeduction savedDeduction = salaryDeductionService.createDeduction(deduction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Salary deduction created successfully", savedDeduction));
    }

    @GetMapping("/my-deductions")
    @PreAuthorize("hasAuthority('WORKER')")
    @Operation(summary = "Get my salary deductions", description = "Get current worker's salary deductions (Workers only)")
    public ResponseEntity<ApiResponse<List<SalaryDeduction>>> getMyDeductions() {
        User currentUser = userService.getCurrentUser();
        Worker worker = workerService.getWorkerByUserId(currentUser.getId());
        List<SalaryDeduction> deductions = salaryDeductionService.getWorkerDeductions(worker);
        return ResponseEntity.ok(ApiResponse.success("Your salary deductions retrieved", deductions));
    }
}
