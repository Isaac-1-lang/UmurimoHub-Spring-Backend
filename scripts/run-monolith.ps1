# Single-process monolith (default mode, port 8081)
$ErrorActionPreference = "Stop"
. (Join-Path $PSScriptRoot "env.ps1")
Set-Location $script:UmurimoHubRoot
Write-Host "Starting monolith on http://localhost:8081"
& mvn spring-boot:run
