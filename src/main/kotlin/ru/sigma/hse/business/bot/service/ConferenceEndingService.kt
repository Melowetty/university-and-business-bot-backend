package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import ru.sigma.hse.business.bot.notification.impl.BatchNotificationService
import ru.sigma.hse.business.bot.notification.model.ConferenceFinishNotification
import ru.sigma.hse.business.bot.utils.Paginator

@Service
class ConferenceEndingService(
    private val taskService: TaskService,
    private val userService: UserService,
    private val notificationService: BatchNotificationService,
) {
    @Value("\${conference.survey}")
    private lateinit var surveyUrl: String

    @Transactional
    fun endConference() {
        logger.info { "Ending all tasks" }
        taskService.endAllTasks()

        logger.info { "Run ending conference notifications" }

        Paginator.fetchPageable(
            fetchFunction = { limit, token ->
                userService.getPageableUsers(token, limit)
            }
        ) { users ->
            logger.info { "Fetched ${users.size} users" }
            val notifications = users.map { user ->
                user.tgId to ConferenceFinishNotification(createSurveyLink(user.tgId))
            }

            logger.info { "Sending notifications to ${users.size} users" }
            notificationService.notify(notifications)
        }

        logger.warn { "Conference ended!!!" }
    }

    fun createSurveyLink(tgId: Long): String {
        return UriComponentsBuilder
            .fromUriString(surveyUrl)
            .queryParam("tid", tgId)
            .build()
            .toUriString()
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}