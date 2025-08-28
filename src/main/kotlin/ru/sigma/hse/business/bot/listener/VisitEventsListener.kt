package ru.sigma.hse.business.bot.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.event.CreatedVisitableObjectEvent
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.persistence.FileStorage
import ru.sigma.hse.business.bot.service.VisitService

@Component
class VisitEventsListener(
    private val visitService: VisitService,
    private val fileStorage: FileStorage,
) {
    @EventListener
    @Async
    fun onCreatedVisitableObjectEvent(event: CreatedVisitableObjectEvent) {
        log.info { "Fetched created visitable object event: $event" }

        val qrCode = visitService.generateVisitQrCode(event.code)

        val path = when(event.data) {
            is Company -> "Компании"
            is Activity -> "Активности"
        }

        val fullPath = "$path/qr_${event.data.name}.png"
        fileStorage.save(fullPath, qrCode)
    }

    companion object {
        private val log = KotlinLogging.logger {  }
    }
}