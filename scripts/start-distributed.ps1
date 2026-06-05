# Start UmurimoHub as distributed microservices with Eureka discovery.
# Prerequisites: PostgreSQL with umurimo_auth, umurimo_worker, umurimo_attendance, umurimo_payroll, umurimo_discipline
# Set DB_PASSWORD if needed: $env:DB_PASSWORD="yourpassword"

$ErrorActionPreference = "Stop"
$root = Split-Path $PSScriptRoot -Parent
Set-Location $root

if (-not $env:JAVA_HOME) {
    $env:JAVA_HOME = "C:\Program Files\Java\jdk-24"
}

$mvn = "mvn"
$common = "-Dspring-boot.run.jvmArguments=-Deureka.client.registry-fetch-interval-seconds=5"

function Start-ServiceWindow($title, $mainClass) {
    $cmd = "cd `"$root`"; `$env:JAVA_HOME=`"$env:JAVA_HOME`"; if (`$env:DB_PASSWORD) {{}}; mvn -q spring-boot:run -Dspring-boot.run.mainClass=$mainClass $common"
    if ($env:DB_PASSWORD) {
        $cmd = "cd `"$root`"; `$env:JAVA_HOME=`"$env:JAVA_HOME`"; `$env:DB_PASSWORD=`"$env:DB_PASSWORD`"; mvn -q spring-boot:run -Dspring-boot.run.mainClass=$mainClass $common"
    }
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $cmd -WindowStyle Normal
    Write-Host "Started $title"
    Start-Sleep -Seconds 3
}

Write-Host "=== UmurimoHub Distributed Stack ==="
Write-Host "Eureka UI:  http://localhost:8761"
Write-Host "API Gateway: http://localhost:8080  (point frontend API here)"
Write-Host ""

Start-ServiceWindow "Eureka Server" "ac.cursor.umurimohub.launcher.EurekaServerApplication"
Start-Sleep -Seconds 15

Start-ServiceWindow "Auth Service :8101" "ac.cursor.umurimohub.launcher.AuthServiceApplication"
Start-ServiceWindow "Worker Service :8102" "ac.cursor.umurimohub.launcher.WorkerServiceApplication"
Start-ServiceWindow "Attendance :8103" "ac.cursor.umurimohub.launcher.AttendanceServiceApplication"
Start-ServiceWindow "Payroll :8104" "ac.cursor.umurimohub.launcher.PayrollServiceApplication"
Start-ServiceWindow "Discipline :8105" "ac.cursor.umurimohub.launcher.DisciplineServiceApplication"
Start-ServiceWindow "Dashboard BFF :8081" "ac.cursor.umurimohub.launcher.DashboardServiceApplication"
Start-Sleep -Seconds 5
Start-ServiceWindow "API Gateway :8080" "ac.cursor.umurimohub.launcher.ApiGatewayApplication"

Write-Host ""
Write-Host "All processes launched in separate windows."
Write-Host "Check Eureka at http://localhost:8761 for registered instances."
