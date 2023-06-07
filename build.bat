@echo off
cd /d "%~dp0."
echo "0/5 Build on Windows"
pause
cls
echo "1/5 Deleate target and out"
rmdir /s target
rmdir /s out
pause
cls
echo "2/5 Build jar with dependencies"
start mvnw install
start mvnw dependency:copy-dependencies
pause
cls
echo "3/5 Make out folder"
mkdir out
cd out
mkdir exe
mkdir jar
cd ..
mkdir .\out\jar\jdk-19
xcopy /S /E .\jdk-19\* .\out\jar\jdk-19\
mkdir .\out\exe\jdk-19
xcopy /S /E .\jdk-19\* .\out\exe\jdk-19\
mkdir .\out\jar\dependency
xcopy /S /E .\target\dependency\* .\out\jar\dependency\
mkdir .\out\exe\dependency
xcopy /S /E .\target\dependency\* .\out\exe\dependency\
echo f|xcopy /y .\target\SMTPAutoSendMessage-1.0-SNAPSHOT.jar /F .\out\jar\smtpautomess.jar
pause
cls
echo "4/5 Build exe"
start ./Launch4j/launch4jc.exe buildLaunch4j.xml
pause
cls
echo "5/5 Build done"
pause
exit