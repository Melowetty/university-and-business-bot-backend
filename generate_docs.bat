@echo off
chcp 65001 >nul

echo 🚀 Starting OpenAPI documentation generation...

:: Очищаем предыдущие сборки
call gradlew clean

set SPRING_PROFILES_ACTIVE=openapi-gen
call gradlew generateOpenApiDocs

if %errorlevel% neq 0 (
    echo ❌ Error during OpenAPI generation!
    exit /b 1
)

echo ✅ OpenAPI documentation generated successfully!
echo 📁 File location: build\api-docs\openapi.json

if exist "build\api-docs\openapi.json" (
    for /f "usebackq" %%A in ('dir "build\api-docs\openapi.json" ^| find "bytes"') do (
        echo 📊 File size: %%~zA bytes
    )
) else (
    echo ❌ Error: openapi.json file not found!
    exit /b 1
)

echo.
echo 🎉 Script completed successfully!
pause