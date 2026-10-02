@echo off
setlocal
cd /d "%~dp0"
set "JDK_HOME=%JAVA_HOME%"
if not defined JDK_HOME for /d %%D in ("%ProgramFiles%\Java\jdk-*") do set "JDK_HOME=%%~fD"
if exist build rmdir /s /q build
mkdir build\server-classes
mkdir build\client-classes
if defined JDK_HOME (
  "%JDK_HOME%\bin\javac.exe" --release 17 -encoding UTF-8 -d build\server-classes src\shared\ru\laba\film\shared\*.java src\server\ru\laba\film\server\domain\*.java src\server\ru\laba\film\server\data\*.java src\server\ru\laba\film\server\service\*.java src\server\ru\laba\film\server\presentation\*.java
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\javac.exe" --release 17 -encoding UTF-8 -d build\client-classes src\shared\ru\laba\film\shared\*.java src\client\ru\laba\film\client\data\*.java src\client\ru\laba\film\client\service\*.java src\client\ru\laba\film\client\presentation\*.java
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\jar.exe" --create --file film-server.jar --main-class ru.laba.film.server.presentation.ServerMain -C build\server-classes .
  if errorlevel 1 exit /b 1
  "%JDK_HOME%\bin\jar.exe" --create --file film-client.jar --main-class ru.laba.film.client.presentation.ClientMain -C build\client-classes .
) else (
  javac --release 17 -encoding UTF-8 -d build\server-classes src\shared\ru\laba\film\shared\*.java src\server\ru\laba\film\server\domain\*.java src\server\ru\laba\film\server\data\*.java src\server\ru\laba\film\server\service\*.java src\server\ru\laba\film\server\presentation\*.java
  if errorlevel 1 exit /b 1
  javac --release 17 -encoding UTF-8 -d build\client-classes src\shared\ru\laba\film\shared\*.java src\client\ru\laba\film\client\data\*.java src\client\ru\laba\film\client\service\*.java src\client\ru\laba\film\client\presentation\*.java
  if errorlevel 1 exit /b 1
  jar --create --file film-server.jar --main-class ru.laba.film.server.presentation.ServerMain -C build\server-classes .
  if errorlevel 1 exit /b 1
  jar --create --file film-client.jar --main-class ru.laba.film.client.presentation.ClientMain -C build\client-classes .
)
if errorlevel 1 exit /b 1
echo Built film-server.jar and film-client.jar
endlocal
