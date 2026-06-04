package ac.cursor.umurimohub.dashboard.controller;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.attendance.service.AttendanceService;
import ac.cursor.umurimohub.discipline.service.DisciplinaryActionService;
import ac.cursor.umurimohub.payroll.service.SalaryDeductionService;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dashboard statistics APIs")
@SecurityRequirement(name = "bearerAuth")
public class DashboardRestController {

    private final WorkerLookupPort workerLookupPort;
    private final AttendanceService attendanceService;
    private final DisciplinaryActionService disciplinaryActionService;
    private final SalaryDeductionService salaryDeductionService;

    public DashboardRestController(WorkerLookupPort workerLookupPort, AttendanceService attendanceService,
                                   DisciplinaryActionService disciplinaryActionService,
                                   SalaryDeductionService salaryDeductionService) {
        this.workerLookupPort = workerLookupPort;
        this.attendanceService = attendanceService;
        this.disciplinaryActionService = disciplinaryActionService;
        this.salaryDeductionService = salaryDeductionService;
    }

    @GetMapping("/ceo-stats")
    @PreAuthorize("hasAuthority('CEO')")
    @Operation(summary = "Get CEO dashboard statistics", description = "Get operational summary for CEO")
    public ResponseEntity<ApiResponse<CEOStats>> getCEOStats() {
        long totalWorkers = workerLookupPort.countWorkers();
        long activeWorkers = workerLookupPort.findAll().stream()
                .filter(w -> "ACTIVE".equals(w.status()))
                .count();
        
        // Get today's attendance
        java.time.LocalDate today = java.time.LocalDate.now();
        long totalAttendanceToday = attendanceService.getAllAttendance().stream()
                .filter(a -> a.getDate().equals(today))
                .count();
        long presentToday = attendanceService.getAllAttendance().stream()
                .filter(a -> a.getDate().equals(today) && a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                .count();
        long absentToday = attendanceService.getAllAttendance().stream()
                .filter(a -> a.getDate().equals(today) && a.getStatus() == Attendance.AttendanceStatus.ABSENT)
                .count();
        
        long totalDeductions = salaryDeductionService.getAllDeductions().size();
        long totalDisciplinaryActions = disciplinaryActionService.getAllActions().size();
       long activeDisciplinaryActions = disciplinaryActionService.getAllActions().stream()
        .filter(a ->
            a.getSeverity() == DisciplinaryAction.Severity.HIGH ||
            a.getSeverity() == DisciplinaryAction.Severity.CRITICAL
        )
        .count();
        
        CEOStats stats = new CEOStats(
                (int) totalWorkers,
                (int) activeWorkers,
                (int) totalAttendanceToday,
                (int) presentToday,
                (int) absentToday,
                (int) totalDeductions,
                (int) totalDisciplinaryActions,
                (int) activeDisciplinaryActions
        );
        return ResponseEntity.ok(ApiResponse.success("CEO dashboard stats retrieved", stats));
    }

    @GetMapping("/hr-stats")
    @PreAuthorize("hasAuthority('HR')")
    @Operation(summary = "Get HR dashboard statistics", description = "Get workforce statistics for HR")
    public ResponseEntity<ApiResponse<HRStats>> getHRStats() {
        long totalWorkers = workerLookupPort.countWorkers();
        long activeWorkers = workerLookupPort.findAll().stream()
                .filter(w -> "ACTIVE".equals(w.status()))
                .count();
        
        // Get today's attendance
        java.time.LocalDate today = java.time.LocalDate.now();
        long attendanceToday = attendanceService.getAllAttendance().stream()
                .filter(a -> a.getDate().equals(today))
                .count();
        
        long pendingDeductions = salaryDeductionService.getAllDeductions().size();
        long openDisciplinaryActions = disciplinaryActionService.getAllActions().size();
        
        HRStats stats = new HRStats(
                (int) totalWorkers,
                (int) activeWorkers,
                (int) attendanceToday,
                (int) pendingDeductions,
                (int) openDisciplinaryActions
        );
        return ResponseEntity.ok(ApiResponse.success("HR dashboard stats retrieved", stats));
    }

    public static class CEOStats {
        private int totalWorkers;
        private int activeWorkers;
        private int totalAttendanceToday;
        private int presentToday;
        private int absentToday;
        private int totalDeductions;
        private int totalDisciplinaryActions;
        private int activeDisciplinaryActions;

        public CEOStats() {
        }

        public CEOStats(int totalWorkers, int activeWorkers, int totalAttendanceToday,
                       int presentToday, int absentToday, int totalDeductions,
                       int totalDisciplinaryActions, int activeDisciplinaryActions) {
            this.totalWorkers = totalWorkers;
            this.activeWorkers = activeWorkers;
            this.totalAttendanceToday = totalAttendanceToday;
            this.presentToday = presentToday;
            this.absentToday = absentToday;
            this.totalDeductions = totalDeductions;
            this.totalDisciplinaryActions = totalDisciplinaryActions;
            this.activeDisciplinaryActions = activeDisciplinaryActions;
        }

        public int getTotalWorkers() {
            return totalWorkers;
        }

        public void setTotalWorkers(int totalWorkers) {
            this.totalWorkers = totalWorkers;
        }

        public int getActiveWorkers() {
            return activeWorkers;
        }

        public void setActiveWorkers(int activeWorkers) {
            this.activeWorkers = activeWorkers;
        }

        public int getTotalAttendanceToday() {
            return totalAttendanceToday;
        }

        public void setTotalAttendanceToday(int totalAttendanceToday) {
            this.totalAttendanceToday = totalAttendanceToday;
        }

        public int getPresentToday() {
            return presentToday;
        }

        public void setPresentToday(int presentToday) {
            this.presentToday = presentToday;
        }

        public int getAbsentToday() {
            return absentToday;
        }

        public void setAbsentToday(int absentToday) {
            this.absentToday = absentToday;
        }

        public int getTotalDeductions() {
            return totalDeductions;
        }

        public void setTotalDeductions(int totalDeductions) {
            this.totalDeductions = totalDeductions;
        }

        public int getTotalDisciplinaryActions() {
            return totalDisciplinaryActions;
        }

        public void setTotalDisciplinaryActions(int totalDisciplinaryActions) {
            this.totalDisciplinaryActions = totalDisciplinaryActions;
        }

        public int getActiveDisciplinaryActions() {
            return activeDisciplinaryActions;
        }

        public void setActiveDisciplinaryActions(int activeDisciplinaryActions) {
            this.activeDisciplinaryActions = activeDisciplinaryActions;
        }
    }

    public static class HRStats {
        private int totalWorkers;
        private int activeWorkers;
        private int attendanceToday;
        private int pendingDeductions;
        private int openDisciplinaryActions;

        public HRStats() {
        }

        public HRStats(int totalWorkers, int activeWorkers, int attendanceToday,
                      int pendingDeductions, int openDisciplinaryActions) {
            this.totalWorkers = totalWorkers;
            this.activeWorkers = activeWorkers;
            this.attendanceToday = attendanceToday;
            this.pendingDeductions = pendingDeductions;
            this.openDisciplinaryActions = openDisciplinaryActions;
        }

        public int getTotalWorkers() {
            return totalWorkers;
        }

        public void setTotalWorkers(int totalWorkers) {
            this.totalWorkers = totalWorkers;
        }

        public int getActiveWorkers() {
            return activeWorkers;
        }

        public void setActiveWorkers(int activeWorkers) {
            this.activeWorkers = activeWorkers;
        }

        public int getAttendanceToday() {
            return attendanceToday;
        }

        public void setAttendanceToday(int attendanceToday) {
            this.attendanceToday = attendanceToday;
        }

        public int getPendingDeductions() {
            return pendingDeductions;
        }

        public void setPendingDeductions(int pendingDeductions) {
            this.pendingDeductions = pendingDeductions;
        }

        public int getOpenDisciplinaryActions() {
            return openDisciplinaryActions;
        }

        public void setOpenDisciplinaryActions(int openDisciplinaryActions) {
            this.openDisciplinaryActions = openDisciplinaryActions;
        }
    }
}
