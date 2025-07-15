@echo off
echo Iniciando Sistema de Financas Pessoais...
echo.
echo Certifique-se de que:
echo 1. O MySQL esta rodando
echo 2. O banco de dados financas_pessoais foi criado
echo 3. As credenciais no DatabaseConnection.java estao corretas
echo.
pause
java -jar target/finance-app-1.0-SNAPSHOT.jar
pause 