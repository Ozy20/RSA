@echo off 
set "SCRIPT_DIR=%%~dp0" 
"%%SCRIPT_DIR%%jre\bin\java.exe" --module-path "%%SCRIPT_DIR%%lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics -jar "%%SCRIPT_DIR%%RSA-1.0-SNAPSHOT.jar" 
