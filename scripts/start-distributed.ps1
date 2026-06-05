# Start UmurimoHub as distributed microservices with Eureka discovery.
# Prerequisites: PostgreSQL databases umurimo_auth, umurimo_worker, etc.
#
# Usage (from project root):
#   .\scripts\start-distributed.ps1
#
# Or set DB password first:
#   $env:DB_PASSWORD = "yourpassword"
#   .\scripts\start-distributed.ps1

$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "env.ps1")

$runScript = Join-Path $PSScriptRoot "run-service.ps1"

function Start-UmurimoService {
    param(
        [string]$Title,
        [string]$MainClass
    )
    $args = @(
        "-NoExit",
        "-ExecutionPolicy", "Bypass",
        "-File", $runScript,
        "-MainClass", $MainClass,
        "-Title", $Title
    )
    Start-Process -FilePath "powershell.exe" -ArgumentList $args -WorkingDirectory $script:UmurimoHubRoot
    Write-Host "Launched: $Title"
    Start-Sleep -Seconds 3
}

Write-Host "=== UmurimoHub Distributed Stack ===" -ForegroundColor Green
Write-Host "Eureka UI:    http://localhost:8761"
Write-Host "API Gateway:  http://localhost:8080"
Write-Host "Project root: $script:UmurimoHubRoot"
Write-Host ""

Start-UmurimoService "Eureka Server :8761" "ac.cursor.umurimohub.launcher.EurekaServerApplication"
Write-Host "Waiting 15s for Eureka..."
Start-Sleep -Seconds 15

Start-UmurimoService "Auth Service :8101" "ac.cursor.umurimohub.launcher.AuthServiceApplication"
Start-UmurimoService "Worker Service :8102" "ac.cursor.umurimohub.launcher.WorkerServiceApplication"
Start-UmurimoService "Attendance :8103" "ac.cursor.umurimohub.launcher.AttendanceServiceApplication"
Start-UmurimoService "Payroll :8104" "ac.cursor.umurimohub.launcher.PayrollServiceApplication"
Start-UmurimoService "Discipline :8105" "ac.cursor.umurimohub.launcher.DisciplineServiceApplication"
Start-UmurimoService "Dashboard BFF :8081" "ac.cursor.umurimohub.launcher.DashboardServiceApplication"

Write-Host "Waiting 5s before gateway..."
Start-Sleep -Seconds 5
Start-UmurimoService "API Gateway :8080" "ac.cursor.umurimohub.launcher.ApiGatewayApplication"

Write-Host ""
Write-Host "All services launched in separate windows." -ForegroundColor Green
Write-Host "Check http://localhost:8761 for registered instances."
