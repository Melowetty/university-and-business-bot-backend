#!/bin/bash

# Скрипт для генерации OpenAPI документации

set -e

echo "🚀 Starting OpenAPI documentation generation..."

# Очищаем предыдущие сборки
./gradlew clean

SPRING_PROFILES_ACTIVE=openapi-gen ./gradlew generateOpenApiDocs

echo "✅ OpenAPI documentation generated successfully!"
echo "📁 File location: build/api-docs/openapi.json"

if [ -f "build/api-docs/openapi.json" ]; then
    echo "📊 File size: $(du -h build/api-docs/openapi.json | cut -f1)"
else
    echo "❌ Error: openapi.json file not found!"
    exit 1
fi