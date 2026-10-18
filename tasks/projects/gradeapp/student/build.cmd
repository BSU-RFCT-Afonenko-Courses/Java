@echo off
rem Provided build helper; no student implementation is needed here.
setlocal
set "PROJECT_ROOT=%~dp0"
if not exist "%PROJECT_ROOT%pom.xml" (
    >&2 echo gradeapp build: required file not found: pom.xml
    exit /b 2
)
if not exist "%PROJECT_ROOT%mvnw.cmd" (
    >&2 echo gradeapp build: required file not found: mvnw.cmd
    exit /b 2
)
if not exist "%PROJECT_ROOT%wrapper\maven-wrapper.properties" (
    >&2 echo gradeapp build: required file not found: wrapper/maven-wrapper.properties
    exit /b 2
)
where powershell >nul 2>&1
if errorlevel 1 (
    >&2 echo gradeapp build: required tool not found: powershell
    exit /b 2
)
if not exist "%PROJECT_ROOT%.mvn\wrapper\" mkdir "%PROJECT_ROOT%.mvn\wrapper" >nul 2>&1
if not exist "%PROJECT_ROOT%.mvn\wrapper\" (
    >&2 echo gradeapp build: cannot prepare .mvn/wrapper/maven-wrapper.properties
    exit /b 2
)
copy /b /y "%PROJECT_ROOT%wrapper\maven-wrapper.properties" "%PROJECT_ROOT%.mvn\wrapper\maven-wrapper.properties" >nul 2>&1
if errorlevel 1 (
    >&2 echo gradeapp build: cannot prepare .mvn/wrapper/maven-wrapper.properties
    exit /b 2
)
pushd "%PROJECT_ROOT%" >nul 2>&1
if errorlevel 1 (
    >&2 echo gradeapp build: cannot enter project root
    exit /b 2
)
call "%PROJECT_ROOT%mvnw.cmd" %*
set "MAVEN_STATUS=%ERRORLEVEL%"
popd
exit /b %MAVEN_STATUS%
