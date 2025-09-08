package ru.sigma.hse.business.bot.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.impl.StubNotificationService
import ru.sigma.hse.business.bot.persistence.FileStorage
import ru.sigma.hse.business.bot.persistence.impl.StubFileStorage


@Configuration
class IntegrationsConfiguration {
    @Bean
    @ConditionalOnMissingBean(FileStorage::class)
    fun stubFileStorage(): FileStorage {
        logger.info { "StubFileStorage is used" }
        return StubFileStorage()
    }

    @Bean
    @ConditionalOnMissingBean(NotificationService::class)
    fun stubNotificationService(): NotificationService {
        logger.info { "StubNotificationService is used" }
        return StubNotificationService()
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}