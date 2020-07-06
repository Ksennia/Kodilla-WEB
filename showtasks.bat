call "runcrud.bat"
if "%ERRORLEVEL%" == "0" goto openbrauser
echo.
echo Can't opened Runcrud

:openbrauser
start firefox http://localhost:8080/crud/v1/task/getTasks
echo.
echo Work is finished.