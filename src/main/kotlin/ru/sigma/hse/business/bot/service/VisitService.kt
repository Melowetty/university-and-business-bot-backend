package ru.sigma.hse.business.bot.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import ru.sigma.hse.business.bot.api.controller.model.VisitResult
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.domain.model.Visitable
import ru.sigma.hse.business.bot.exception.activity.ActivityByIdNotFoundException
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.exception.visit.BadVisitCodeException
import ru.sigma.hse.business.bot.notification.NotificationService
import ru.sigma.hse.business.bot.notification.model.UserVisitNotification
import ru.sigma.hse.business.bot.persistence.ActivityStorage
import ru.sigma.hse.business.bot.persistence.CompanyStorage
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.persistence.VisitStorage
import ru.sigma.hse.business.bot.service.qr.QrCodeGenerator

@Service
class VisitService(
    private val visitStorage: VisitStorage,
    private val companyStorage: CompanyStorage,
    private val activityStorage: ActivityStorage,
    private val qrCodeGenerator: QrCodeGenerator,
    private val userStorage: UserStorage,
    private val notificationService: NotificationService,

    @Value("\${conference.count-for-complete}")
    private val countForCompleteConference: Int,
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

        val visitsCount = visitStorage.getCountVisitsByUserId(userId)

        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        var completeConference = false
        if (visitsCount >= countForCompleteConference && !user.isCompleteConference) {
            userStorage.markUserAsCompletedConference(userId)
            completeConference = true
        }

        return VisitResult(
            target = visit.target,
            targetType = visit.type,
            isCompleteConference = completeConference
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
        val user = userStorage.findUserByCode(userCode)
            ?: throw BadArgumentException("WRONG_USER_CODE", "Wrong user code")

        val activity = activityStorage.getActivity(activityId)
            ?: throw ActivityByIdNotFoundException(activityId)

        visitStorage.addActivityVisit(user.id, activityId)
        userStorage.addPointsToUser(user.id, 5)

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