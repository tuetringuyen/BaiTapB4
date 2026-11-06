@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"

set "JAVA=java"
set "JAVAC=javac"
if not defined JAVA_HOME goto tools_ready
if not exist "%JAVA_HOME%\bin\javac.exe" goto tools_ready
set "JAVA=%JAVA_HOME%\bin\java.exe"
set "JAVAC=%JAVA_HOME%\bin\javac.exe"

:tools_ready
"%JAVAC%" -version >nul 2>&1
if errorlevel 1 goto no_jdk
"%JAVA%" -version >nul 2>&1
if errorlevel 1 goto no_jdk

set "MAIN=vn.edu.baitap.Main"
set "APP_ARGS="
set "MODE=%~1"
if "%MODE%"=="" set "MODE=menu"
if /I "%MODE%"=="menu" goto compile
if /I "%MODE%"=="demo" goto demo
if /I "%MODE%"=="bai1" goto bai1
if /I "%MODE%"=="bai2" goto bai2
if /I "%MODE%"=="bai3" goto bai3
if /I "%MODE%"=="test" goto test
 echo Cach dung: chay.bat [menu^|demo^|bai1^|bai2^|bai3^|test]
exit /b 2

:demo
set "APP_ARGS=--demo"
goto compile
:bai1
set "MAIN=vn.edu.baitap.Bai1"
goto compile
:bai2
set "MAIN=vn.edu.baitap.Bai2"
goto compile
:bai3
set "MAIN=vn.edu.baitap.Bai3"
goto compile
:test
set "MAIN=vn.edu.baitap.KiemThu"
goto compile

:compile
if not exist "out\classes" mkdir "out\classes"
if not exist "out\test-classes" mkdir "out\test-classes"
"%JAVAC%" -encoding UTF-8 --release 17 -d "out\classes" src\main\java\vn\edu\baitap\*.java
if errorlevel 1 goto compile_error
set "APP_CLASSPATH=out\classes"
if /I "%MODE%"=="test" goto compile_tests
goto run

:compile_tests
"%JAVAC%" -encoding UTF-8 --release 17 -cp "out\classes" -d "out\test-classes" src\test\java\vn\edu\baitap\*.java
if errorlevel 1 goto compile_error
set "APP_CLASSPATH=out\classes;out\test-classes"

:run
"%JAVA%" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "%APP_CLASSPATH%" %MAIN% %APP_ARGS%
exit /b %errorlevel%

:no_jdk
echo Khong tim thay Java JDK. Hay cai JDK 17 hoac 21 va cau hinh JAVA_HOME/PATH.
pause
exit /b 1

:compile_error
echo Bien dich that bai. Kiem tra JDK 17 tro len va thong bao loi phia tren.
pause
exit /b 1
