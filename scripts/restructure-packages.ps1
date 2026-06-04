# Modular monolith package restructure
$ErrorActionPreference = "Stop"
$root = Join-Path $PSScriptRoot "..\src\main\java\ac\cursor\umurimohub"
$root = (Resolve-Path $root).Path

$moves = @(
    @{ From = "dto\ApiResponse.java"; To = "shared\dto\ApiResponse.java"; Pkg = "ac.cursor.umurimohub.shared.dto" }
    @{ From = "exception\GlobalExceptionHandler.java"; To = "shared\exception\GlobalExceptionHandler.java"; Pkg = "ac.cursor.umurimohub.shared.exception" }

    @{ From = "controller\AuthController.java"; To = "auth\controller\AuthController.java"; Pkg = "ac.cursor.umurimohub.auth.controller" }
    @{ From = "controller\UserRestController.java"; To = "auth\controller\UserRestController.java"; Pkg = "ac.cursor.umurimohub.auth.controller" }
    @{ From = "dto\RegisterRequest.java"; To = "auth\dto\RegisterRequest.java"; Pkg = "ac.cursor.umurimohub.auth.dto" }
    @{ From = "dto\LoginRequest.java"; To = "auth\dto\LoginRequest.java"; Pkg = "ac.cursor.umurimohub.auth.dto" }
    @{ From = "dto\VerifyOtpRequest.java"; To = "auth\dto\VerifyOtpRequest.java"; Pkg = "ac.cursor.umurimohub.auth.dto" }
    @{ From = "dto\AuthResponse.java"; To = "auth\dto\AuthResponse.java"; Pkg = "ac.cursor.umurimohub.auth.dto" }
    @{ From = "dto\UserRequest.java"; To = "auth\dto\UserRequest.java"; Pkg = "ac.cursor.umurimohub.auth.dto" }
    @{ From = "model\User.java"; To = "auth\model\User.java"; Pkg = "ac.cursor.umurimohub.auth.model" }
    @{ From = "model\Otp.java"; To = "auth\model\Otp.java"; Pkg = "ac.cursor.umurimohub.auth.model" }
    @{ From = "repository\UserRepository.java"; To = "auth\repository\UserRepository.java"; Pkg = "ac.cursor.umurimohub.auth.repository" }
    @{ From = "repository\OtpRepository.java"; To = "auth\repository\OtpRepository.java"; Pkg = "ac.cursor.umurimohub.auth.repository" }
    @{ From = "service\UserService.java"; To = "auth\service\UserService.java"; Pkg = "ac.cursor.umurimohub.auth.service" }
    @{ From = "service\OtpService.java"; To = "auth\service\OtpService.java"; Pkg = "ac.cursor.umurimohub.auth.service" }

    @{ From = "controller\WorkerRestController.java"; To = "worker\controller\WorkerRestController.java"; Pkg = "ac.cursor.umurimohub.worker.controller" }
    @{ From = "dto\WorkerRequest.java"; To = "worker\dto\WorkerRequest.java"; Pkg = "ac.cursor.umurimohub.worker.dto" }
    @{ From = "model\Worker.java"; To = "worker\model\Worker.java"; Pkg = "ac.cursor.umurimohub.worker.model" }
    @{ From = "repository\WorkerRepository.java"; To = "worker\repository\WorkerRepository.java"; Pkg = "ac.cursor.umurimohub.worker.repository" }
    @{ From = "service\WorkerService.java"; To = "worker\service\WorkerService.java"; Pkg = "ac.cursor.umurimohub.worker.service" }

    @{ From = "controller\AttendanceRestController.java"; To = "attendance\controller\AttendanceRestController.java"; Pkg = "ac.cursor.umurimohub.attendance.controller" }
    @{ From = "controller\ReactiveLearnController.java"; To = "attendance\controller\ReactiveLearnController.java"; Pkg = "ac.cursor.umurimohub.attendance.controller" }
    @{ From = "dto\AttendanceRequest.java"; To = "attendance\dto\AttendanceRequest.java"; Pkg = "ac.cursor.umurimohub.attendance.dto" }
    @{ From = "dto\AttendanceLiveEvent.java"; To = "attendance\dto\AttendanceLiveEvent.java"; Pkg = "ac.cursor.umurimohub.attendance.dto" }
    @{ From = "model\Attendance.java"; To = "attendance\model\Attendance.java"; Pkg = "ac.cursor.umurimohub.attendance.model" }
    @{ From = "repository\AttendanceRepository.java"; To = "attendance\repository\AttendanceRepository.java"; Pkg = "ac.cursor.umurimohub.attendance.repository" }
    @{ From = "service\AttendanceService.java"; To = "attendance\service\AttendanceService.java"; Pkg = "ac.cursor.umurimohub.attendance.service" }
    @{ From = "service\ReactiveAttendanceService.java"; To = "attendance\service\ReactiveAttendanceService.java"; Pkg = "ac.cursor.umurimohub.attendance.service" }

    @{ From = "controller\SalaryDeductionRestController.java"; To = "payroll\controller\SalaryDeductionRestController.java"; Pkg = "ac.cursor.umurimohub.payroll.controller" }
    @{ From = "dto\SalaryDeductionRequest.java"; To = "payroll\dto\SalaryDeductionRequest.java"; Pkg = "ac.cursor.umurimohub.payroll.dto" }
    @{ From = "model\SalaryDeduction.java"; To = "payroll\model\SalaryDeduction.java"; Pkg = "ac.cursor.umurimohub.payroll.model" }
    @{ From = "repository\SalaryDeductionRepository.java"; To = "payroll\repository\SalaryDeductionRepository.java"; Pkg = "ac.cursor.umurimohub.payroll.repository" }
    @{ From = "service\SalaryDeductionService.java"; To = "payroll\service\SalaryDeductionService.java"; Pkg = "ac.cursor.umurimohub.payroll.service" }

    @{ From = "controller\DisciplinaryActionRestController.java"; To = "discipline\controller\DisciplinaryActionRestController.java"; Pkg = "ac.cursor.umurimohub.discipline.controller" }
    @{ From = "dto\DisciplinaryActionRequest.java"; To = "discipline\dto\DisciplinaryActionRequest.java"; Pkg = "ac.cursor.umurimohub.discipline.dto" }
    @{ From = "model\DisciplinaryAction.java"; To = "discipline\model\DisciplinaryAction.java"; Pkg = "ac.cursor.umurimohub.discipline.model" }
    @{ From = "repository\DisciplinaryActionRepository.java"; To = "discipline\repository\DisciplinaryActionRepository.java"; Pkg = "ac.cursor.umurimohub.discipline.repository" }
    @{ From = "service\DisciplinaryActionService.java"; To = "discipline\service\DisciplinaryActionService.java"; Pkg = "ac.cursor.umurimohub.discipline.service" }

    @{ From = "service\EmailService.java"; To = "notification\service\EmailService.java"; Pkg = "ac.cursor.umurimohub.notification.service" }

    @{ From = "controller\DashboardRestController.java"; To = "dashboard\controller\DashboardRestController.java"; Pkg = "ac.cursor.umurimohub.dashboard.controller" }
)

$importReplacements = [ordered]@{
    "ac.cursor.umurimohub.dto.ApiResponse" = "ac.cursor.umurimohub.shared.dto.ApiResponse"
    "ac.cursor.umurimohub.dto.RegisterRequest" = "ac.cursor.umurimohub.auth.dto.RegisterRequest"
    "ac.cursor.umurimohub.dto.LoginRequest" = "ac.cursor.umurimohub.auth.dto.LoginRequest"
    "ac.cursor.umurimohub.dto.VerifyOtpRequest" = "ac.cursor.umurimohub.auth.dto.VerifyOtpRequest"
    "ac.cursor.umurimohub.dto.AuthResponse" = "ac.cursor.umurimohub.auth.dto.AuthResponse"
    "ac.cursor.umurimohub.dto.UserRequest" = "ac.cursor.umurimohub.auth.dto.UserRequest"
    "ac.cursor.umurimohub.dto.WorkerRequest" = "ac.cursor.umurimohub.worker.dto.WorkerRequest"
    "ac.cursor.umurimohub.dto.AttendanceRequest" = "ac.cursor.umurimohub.attendance.dto.AttendanceRequest"
    "ac.cursor.umurimohub.dto.AttendanceLiveEvent" = "ac.cursor.umurimohub.attendance.dto.AttendanceLiveEvent"
    "ac.cursor.umurimohub.dto.SalaryDeductionRequest" = "ac.cursor.umurimohub.payroll.dto.SalaryDeductionRequest"
    "ac.cursor.umurimohub.dto.DisciplinaryActionRequest" = "ac.cursor.umurimohub.discipline.dto.DisciplinaryActionRequest"
    "ac.cursor.umurimohub.model.User" = "ac.cursor.umurimohub.auth.model.User"
    "ac.cursor.umurimohub.model.Otp" = "ac.cursor.umurimohub.auth.model.Otp"
    "ac.cursor.umurimohub.model.Worker" = "ac.cursor.umurimohub.worker.model.Worker"
    "ac.cursor.umurimohub.model.Attendance" = "ac.cursor.umurimohub.attendance.model.Attendance"
    "ac.cursor.umurimohub.model.SalaryDeduction" = "ac.cursor.umurimohub.payroll.model.SalaryDeduction"
    "ac.cursor.umurimohub.model.DisciplinaryAction" = "ac.cursor.umurimohub.discipline.model.DisciplinaryAction"
    "ac.cursor.umurimohub.repository.UserRepository" = "ac.cursor.umurimohub.auth.repository.UserRepository"
    "ac.cursor.umurimohub.repository.OtpRepository" = "ac.cursor.umurimohub.auth.repository.OtpRepository"
    "ac.cursor.umurimohub.repository.WorkerRepository" = "ac.cursor.umurimohub.worker.repository.WorkerRepository"
    "ac.cursor.umurimohub.repository.AttendanceRepository" = "ac.cursor.umurimohub.attendance.repository.AttendanceRepository"
    "ac.cursor.umurimohub.repository.SalaryDeductionRepository" = "ac.cursor.umurimohub.payroll.repository.SalaryDeductionRepository"
    "ac.cursor.umurimohub.repository.DisciplinaryActionRepository" = "ac.cursor.umurimohub.discipline.repository.DisciplinaryActionRepository"
    "ac.cursor.umurimohub.service.UserService" = "ac.cursor.umurimohub.auth.service.UserService"
    "ac.cursor.umurimohub.service.OtpService" = "ac.cursor.umurimohub.auth.service.OtpService"
    "ac.cursor.umurimohub.service.WorkerService" = "ac.cursor.umurimohub.worker.service.WorkerService"
    "ac.cursor.umurimohub.service.AttendanceService" = "ac.cursor.umurimohub.attendance.service.AttendanceService"
    "ac.cursor.umurimohub.service.ReactiveAttendanceService" = "ac.cursor.umurimohub.attendance.service.ReactiveAttendanceService"
    "ac.cursor.umurimohub.service.SalaryDeductionService" = "ac.cursor.umurimohub.payroll.service.SalaryDeductionService"
    "ac.cursor.umurimohub.service.DisciplinaryActionService" = "ac.cursor.umurimohub.discipline.service.DisciplinaryActionService"
    "ac.cursor.umurimohub.service.EmailService" = "ac.cursor.umurimohub.notification.service.EmailService"
}

function Apply-ImportReplacements([string]$content) {
    foreach ($key in $importReplacements.Keys) {
        $content = $content.Replace($key, $importReplacements[$key])
    }
    return $content
}

foreach ($move in $moves) {
    $src = Join-Path $root $move.From
    $dest = Join-Path $root $move.To
    if (-not (Test-Path $src)) {
        Write-Warning "Skip missing: $($move.From)"
        continue
    }
    $destDir = Split-Path $dest -Parent
    New-Item -ItemType Directory -Force -Path $destDir | Out-Null
    $content = Get-Content $src -Raw
    $content = $content -replace "^package\s+[\w.]+;", "package $($move.Pkg);"
    $content = Apply-ImportReplacements $content
    Set-Content -Path $dest -Value $content -NoNewline
    Remove-Item $src -Force
    Write-Host "Moved $($move.From) -> $($move.To)"
}

# Fix remaining files (config, security)
Get-ChildItem -Path $root -Recurse -Filter "*.java" | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    $updated = Apply-ImportReplacements $content
    if ($updated -ne $content) {
        Set-Content -Path $_.FullName -Value $updated -NoNewline
    }
}

# Remove empty legacy folders
@("controller", "dto", "model", "repository", "service", "exception") | ForEach-Object {
    $dir = Join-Path $root $_
    if (Test-Path $dir) {
        Remove-Item $dir -Recurse -Force -ErrorAction SilentlyContinue
    }
}

Write-Host "Package restructure complete."
