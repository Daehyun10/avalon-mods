param(
    [Parameter(Mandatory = $true)][string]$ProjectPath,
    [string]$JarName = 'enhancemod-1.0.1-1.20.1.jar'
)
$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$projectRoot = (Resolve-Path -LiteralPath $ProjectPath).Path
if ([IO.Path]::GetFileName($JarName) -ne $JarName) { throw 'JarName must be a filename.' }
$gradle = Join-Path $projectRoot 'gradlew.bat'
if (-not (Test-Path -LiteralPath $gradle)) { throw 'No Gradle wrapper found in ProjectPath.' }
Push-Location $projectRoot
try {
    & $gradle build
    if ($LASTEXITCODE -ne 0) { throw 'Mod build failed. No distribution files changed.' }
} finally { Pop-Location }
$jar = Join-Path $projectRoot "build/libs/$JarName"
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [IO.Compression.ZipFile]::OpenRead($jar)
try {
    $entry = $zip.GetEntry('META-INF/mods.toml')
    if ($null -eq $entry) { throw 'The JAR is not a Forge mod.' }
    $reader = [IO.StreamReader]::new($entry.Open())
    try { $metadata = $reader.ReadToEnd() } finally { $reader.Dispose() }
    if ($metadata -notmatch 'modId\s*=\s*"enhancemod"') { throw 'Expected the enhancemod mod ID.' }
    if ($metadata -notmatch 'versionRange\s*=\s*"\[1\.20\.1,1\.20\.2\)"') { throw 'Expected Minecraft [1.20.1,1.20.2).' }
} finally { $zip.Dispose() }
$manifestPath = Join-Path $repoRoot 'distribution.json'
$manifest = Get-Content -LiteralPath $manifestPath -Raw | ConvertFrom-Json
$profile = $manifest.servers | Where-Object id -eq 'AVALON-Development-1.20.1'
$module = $profile.modules | Where-Object { $_.type -eq 'ForgeMod' -and $_.id -like 'com.enhance.mod:enhancemod:*' }
if (@($module).Count -ne 1) { throw 'Expected exactly one AVALON mod module.' }
$target = Join-Path $repoRoot "servers/AVALON-Development-1.20.1/forgemods/optionalon/$JarName"
if ($metadata -notmatch '(?m)^\s*version\s*=\s*"([^"]+)"') { throw 'Missing mod version.' }
$modVersion = $Matches[1]
$module.id = "com.enhance.mod:enhancemod:${modVersion}@jar"
$module.artifact.url = "https://raw.githubusercontent.com/Daehyun10/avalon-mods/main/servers/AVALON-Development-1.20.1/forgemods/optionalon/$JarName"
Copy-Item -LiteralPath $jar -Destination $target -Force
$module.artifact.size = (Get-Item -LiteralPath $target).Length
$module.artifact.MD5 = (Get-FileHash -LiteralPath $target -Algorithm MD5).Hash.ToLowerInvariant()
$json = $manifest | ConvertTo-Json -Depth 100
$utf8 = [Text.UTF8Encoding]::new($false)
[IO.File]::WriteAllText($manifestPath, $json.Replace("`r`n", "`n") + "`n", $utf8)
Write-Output "Updated $target ($($module.artifact.size) bytes, MD5 $($module.artifact.MD5))."
Write-Output 'Commit and push distribution.json and servers together to publish this update.'
