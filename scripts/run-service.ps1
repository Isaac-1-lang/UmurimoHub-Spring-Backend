<#
.SYNOPSIS
  Run one UmurimoHub distributed service (Eureka, gateway, or domain service).

.EXAMPLE
  .\scripts\run-service.ps1 -MainClass ac.cursor.umurimohub.launcher.EurekaServerApplication -Title "Eureka :8761"

.EXAMPLE
  .\scripts\run-service.ps1 -MainClass ac.cursor.umurimohub.launcher.AuthServiceApplication -Title "Auth :8101"
#>
param(
    [Parameter(Mandatory = $true)]
    [string]$MainClass,

    [string]$Title = $MainClass
)

$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "env.ps1")

Set-Location $script:UmurimoHubRoot

Write-Host ""
Write-Host "=== $Title ===" -ForegroundColor Cyan
Write-Host "JAVA_HOME: $env:JAVA_HOME"
Write-Host "Project:   $script:UmurimoHubRoot"
Write-Host ""

& mvn spring-boot:run `
    "-Dumurimo.mainClass=$MainClass" `
    "-Dspring-boot.run.jvmArguments=-Deureka.client.registry-fetch-interval-seconds=5"
