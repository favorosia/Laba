@echo off
setlocal
cd /d "%~dp0"
set "DB_USER=%FILM_DB_USER%"
if not defined DB_USER set "DB_USER=postgres"
createdb -U "%DB_USER%" --encoding=UTF8 --template=template0 film_catalog
if errorlevel 1 echo Database creation failed or database already exists; continuing with schema setup.
psql -U "%DB_USER%" -d film_catalog -v ON_ERROR_STOP=1 -f db\schema.sql
if errorlevel 1 exit /b 1
psql -U "%DB_USER%" -d film_catalog -v ON_ERROR_STOP=1 -f db\seed.sql
if errorlevel 1 exit /b 1
echo Schema ready and demo data loaded.
endlocal
