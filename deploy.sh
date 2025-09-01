#!/bin/bash
TARGET_TAG=$1

# Функция для ожидания стабилизации сервисов
wait_for_services() {
    local stack_name=$1
    local timeout=600  # 10 минут таймаут
    local interval=10
    local elapsed=0

    echo "⏳ Ожидаем стабилизации сервисов..."

    while [ $elapsed -lt $timeout ]; do
        # Проверяем все сервисы в stack
        local all_ready=true
        local services=$(docker service ls --filter "name=${stack_name}_" --format "{{.Name}}")

        if [ -z "$services" ]; then
            echo "❌ Не найдено сервисов в stack: $stack_name"
            return 1
        fi

        for service in $services; do
            local replicas=$(docker service ls --filter "name=$service" --format "{{.Replicas}}")
            local current=$(echo $replicas | awk -F'/' '{print $1}')
            local target=$(echo $replicas | awk -F'/' '{print $2}')

            if [ "$current" != "$target" ] || [ "$current" = "0" ]; then
                echo "⏳ Сервис $service: $replicas"
                all_ready=false
            fi
        done

        if [ "$all_ready" = true ]; then
            echo "✅ Все сервисы стабилизированы"
            return 0
        fi

        sleep $interval
        elapsed=$((elapsed + interval))
        echo "⏳ Прошло $elapsed секунд из $timeout"
    done

    echo "❌ Таймаут ожидания сервисов"
    docker service ls --filter "name=${stack_name}_"
    return 1
}

# Пробуем деплоить с указанным тегом
if IMAGE_TAG="ghcr.io/melowetty/ub-backend:$TARGET_TAG" docker stack deploy -c docker-compose.yml ub-backend --with-registry-auth; then
    echo "✅ Деплой запущен с тегом: $TARGET_TAG"

    # Ждем стабилизации сервисов
    if wait_for_services "ub-backend"; then
        echo "✅ Деплой успешно завершен с тегом: $TARGET_TAG"
        exit 0
    else
        echo "❌ Сервисы не стабилизировались с тегом: $TARGET_TAG"
        exit 1
    fi

else
    echo "❌ Деплой с тегом $TARGET_TAG failed, пробуем stable"

    if IMAGE_TAG="ghcr.io/melowetty/ub-backend:stable" docker stack deploy -c docker-compose.yml ub-backend --with-registry-auth; then
        echo "✅ Деплой запущен с тегом stable"

        # Ждем стабилизации сервисов
        if wait_for_services "ub-backend"; then
            echo "✅ Деплой с тегом stable успешно завершен"
            exit 0
        else
            echo "❌ Сервисы не стабилизировались с тегом stable"
            exit 1
        fi

    else
        echo "💥 Все деплои failed"
        exit 1
    fi
fi