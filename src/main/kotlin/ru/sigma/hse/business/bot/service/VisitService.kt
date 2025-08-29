package ru.sigma.hse.business.bot.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.domain.model.Visitable
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
) {
    @Value("\${integrations.telegram.link}")
    private lateinit var telegramBotLink: String

    fun visit(userId: Long, code: String): DetailedVisit {
        val type = code.substring(0, 3)

        when (type) {
            "UNC" -> {
                val targetId = visitStorage.addCompanyVisit(userId, code).targetId
                val company = companyStorage.getCompany(targetId)
                    ?: throw IllegalStateException("Company not found")

                return company.toDetailedVisit()
            }

            "UNA" -> {
                val targetId = visitStorage.addActivityVisit(userId, code).targetId
                val activity = activityStorage.getActivity(targetId)
                    ?: throw IllegalStateException("Activity not found")

                return activity.toDetailedVisit()
            }

            else -> throw IllegalArgumentException("Неверный тип кода")
        }
    }

    fun generateVisitQrCode(code: String): ByteArray {
        val link = UriComponentsBuilder
            .fromUriString(telegramBotLink)
            .queryParam("start", code)
            .build()
            .toUriString()

        return qrCodeGenerator.generateQrCode(link)
    }

    private fun Visitable.toDetailedVisit(): DetailedVisit {
        val type = when (this) {
            is Company -> VisitTarget.COMPANY
            is Activity -> VisitTarget.ACTIVITY
        }

        return DetailedVisit(this, type)
    }

    fun getUserVisits(userId: Long): List<DetailedVisit> {
        val allVisits = visitStorage.getVisitsByUserId(userId)
        val allIds = allVisits.map { it.targetId }

        var detailedVisits = mutableListOf<DetailedVisit>()
        detailedVisits += companyStorage.getCompanies(allIds)
            .map { it.toDetailedVisit() }
//        detailedVisits += activityStorage.getActivities(allIds)
//            .mapNotNull { it?.toDetailedVisit() }

        return detailedVisits.toList()
    }
}