# Dot-source before running services:  . .\scripts\env.ps1
# Or run-service.ps1 loads this automatically.

$env:JAVA_HOME = "C:\Program Files\Java\jdk-24"

# PostgreSQL password for service-* profiles (change if needed)
if (-not $env:DB_PASSWORD) {
    $env:DB_PASSWORD = "121402pr0732021"
}

$script:UmurimoHubRoot = Resolve-Path (Join-Path $PSScriptRoot "..")
