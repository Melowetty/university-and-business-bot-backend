package ru.sigma.hse.business.bot.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import ru.sigma.hse.business.bot.api.controller.model.VisitResult
import ru.sigma.hse.business.bot.domain.model.*
import ru.sigma.hse.business.bot.exception.activity.ActivityByIdNotFoundException
import ru.sigma.hse.business.bot.exception.visit.BadVisitCodeException
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.model.UserVisitNotification
import ru.sigma.hse.business.bot.persistence.ActivityStorage
import ru.sigma.hse.business.bot.persistence.CompanyStorage
import ru.sigma.hse.business.bot.persistence.VisitStorage
import ru.sigma.hse.business.bot.service.qr.QrCodeGenerator

@Service
class VisitService(
    private val visitStorage: VisitStorage,
    private val companyStorage: CompanyStorage,
    private val activityStorage: ActivityStorage,
    private val qrCodeGenerator: QrCodeGenerator,
    private val userService: UserService,
    private val notificationService: NotificationService,
) {
    @Value("\${integrations.telegram.link}")
    private lateinit var telegramBotLink: String

    @Transactional
    fun visit(userId: Long, code: String): VisitResult {
        val type = code.substring(0, 3)

        val visit = when (type) {
            "UNC" -> {
                val company = companyStorage.getCompanyByCode(code)
                    ?: throw BadVisitCodeException()

                visitStorage.addCompanyVisit(userId, company.id)
                company.toDetailedVisit()
            }

            "UNA" -> {
                val activity = activityStorage.getActivityByCode(code)
                    ?: throw BadVisitCodeException()

                visitStorage.addActivityVisit(userId, activity.id).targetId
                activity.toDetailedVisit()
            }

            else -> throw BadVisitCodeException()
        }

        return VisitResult(
            target = visit.target,
            targetType = visit.type,
        )
    }

    fun generateVisitQrCode(code: String, size: Int): ByteArray {
        val link = UriComponentsBuilder
            .fromUriString(telegramBotLink)
            .queryParam("start", code)
            .build()
            .toUriString()

        return qrCodeGenerator.generateQrCode(link, size)
    }

    private fun Visitable.toDetailedVisit(): DetailedVisit {
        val type = when (this) {
            is Company -> VisitTarget.COMPANY
            is Activity -> VisitTarget.ACTIVITY
        }

        return DetailedVisit(this, type)
    }

    @Transactional
    fun markUserAsVisitedActivity(activityId: Long, userCode: String): Activity {
        val user = userService.getUserByCode(userCode)

        val activity = activityStorage.getActivity(activityId)
            ?: throw ActivityByIdNotFoundException(activityId)

        visitStorage.addActivityVisit(user.id, activityId)
        userService.addPointsToUserScore(user.id, activity.points)

        val notification = UserVisitNotification(
            DetailedVisit(
                target = activity,
                type = VisitTarget.ACTIVITY
            )
        )

        notificationService.notify(user.tgId, notification)

        return activity
    }

    fun getUserVisits(userId: Long): List<DetailedVisit> {
        val allVisits = visitStorage.getVisitsByUserId(userId)

        val companyIds = allVisits.filter { it.targetType == VisitTarget.COMPANY }
            .map { it.targetId }

        val activityIds = allVisits.filter { it.targetType == VisitTarget.ACTIVITY }
            .map { it.targetId }


        val detailedVisits = run {
            val companies = companyStorage.getCompanies(companyIds)
            val activities = activityStorage.getActivities(activityIds)
            companies + activities
        }.map { it.toDetailedVisit() }

        return detailedVisits
    }
}