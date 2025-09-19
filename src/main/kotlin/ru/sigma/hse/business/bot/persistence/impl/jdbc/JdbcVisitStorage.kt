package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.exception.activity.ActivityByIdNotFoundException
import ru.sigma.hse.business.bot.exception.company.CompanyByIdNotFoundException
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.exception.visit.VisitAlreadyExistsException
import ru.sigma.hse.business.bot.exception.visit.VisitByIdNotFoundException
import ru.sigma.hse.business.bot.exception.visit.VisitByUserIdTargetIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.VisitRepository

@Component
class JdbcVisitStorage(
    private val visitRepository: VisitRepository,
    private val jdbcCompanyStorage: JdbcCompanyStorage,
    private val jdbcActivityStorage: JdbcActivityStorage,
    private val jdbcUserStorage: JdbcUserStorage,
) {
    fun getVisits(limit: Int, token: Long = 0): Pageable<UserVisit> {
        val visits = visitRepository.findAllByIdGreaterThanOrderByIdAsc(token, limit)

        val companiesById = visits.filter { it.targetType == VisitTarget.COMPANY }.map { it.targetId }
            .let { jdbcCompanyStorage.getCompanies(it) }.associateBy { it.id }

        val activitiesById = visits.filter { it.targetType == VisitTarget.ACTIVITY }.map { it.targetId }
            .let { jdbcActivityStorage.getActivities(it) }.associateBy { it.id }

        val mappedVisits = visits.mapNotNull { visit ->
            val target = when (visit.targetType) {
                VisitTarget.COMPANY -> companiesById[visit.targetId]
                VisitTarget.ACTIVITY -> activitiesById[visit.targetId]
            } ?: run {
                logger.warn { "Target with id ${visit.targetId} and type ${visit.targetType} does not exist" }
                return@mapNotNull null
            }

            UserVisit(
                userId = visit.userId,
                target = target,
                time = visit.time,
            )
        }

        val nextToken = if (visits.size < limit) {
            0L
        } else {
            visits.last().id()
        }

        logger.info { "Fetched ${visits.size} visits starting from token $token" }
        return Pageable(
            data = mappedVisits,
            nextToken = nextToken
        )
    }

    fun getVisitsByTargetId(targetId: Long, limit: Int, token: Long = 0): Pageable<Visit> {
        val data = visitRepository.findAllByTargetIdAndIdGreaterThanOrderByIdAsc(targetId, token, limit)
        val nextToken = if (data.size < limit) {
            0L
        } else {
            data.last().id()
        }

        return Pageable(
            data = data.map { it.toVisit() },
            nextToken = nextToken
        )
    }

    fun addCompanyVisit(userId: Long, companyId: Long): Visit {
        validateUser(userId)
        val company = jdbcCompanyStorage.getCompany(companyId)
            ?: throw CompanyByIdNotFoundException(companyId)

        if (visitRepository.existsByUserIdAndTargetId(userId, company.id)) {
            logger.warn { "Visit to company with id $companyId already exists for user $userId" }
            throw VisitAlreadyExistsException()
        }

        val visitEntity = visitRepository.save(
            VisitEntity(
                userId = userId,
                targetId = company.id,
                targetType = VisitTarget.COMPANY,
                isGotExtraReward = false,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added company visit for user $userId to company ${company.name}" }

        return visitEntity.toVisit()
    }

    fun addActivityVisit(userId: Long, activityId: Long): Visit {
        validateUser(userId)
        val activity = jdbcActivityStorage.getActivity(activityId)
            ?: throw ActivityByIdNotFoundException(activityId)

        if (visitRepository.existsByUserIdAndTargetId(userId, activity.id)) {
            logger.warn { "Visit to activity with id $activityId already exists for user $userId" }
            throw VisitAlreadyExistsException()
        }

        val visitEntity = visitRepository.save(
            VisitEntity(
                userId = userId,
                targetId = activity.id,
                targetType = VisitTarget.ACTIVITY,
                isGotExtraReward = false,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added activity visit for user $userId to activity ${activity.name}" }
        return visitEntity.toVisit()
    }

    fun getVisitsByUserId(userId: Long): List<Visit> {
        if (!jdbcUserStorage.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        return visitRepository.findByUserId(userId).map {
            it.toVisit()
        }
    }

    fun getVisitsCountByUserId(userId: Long): Long {
        validateUser(userId)
        return visitRepository.countByUserId(userId)
    }

    fun getVisitByUserIdTargetId(userId: Long, activityId: Long): Visit {
        val visit = visitRepository.getVisitByUserIdAndTargetId(userId, activityId)
            ?: throw VisitByUserIdTargetIdNotFoundException(userId, activityId)
        return visit.toVisit()
    }

    fun setIsGotExtraReward(visitId: Long) {
        if (!visitRepository.existsById(visitId)){
            logger.info { "Visit with id $visitId does not exist" }
            throw VisitByIdNotFoundException(visitId)
        }
        visitRepository.setIsGotExtraReward(visitId)
    }

    private fun validateUser(userId: Long) {
        if (!jdbcUserStorage.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun VisitEntity.toVisit(): Visit {
            return Visit(
                id = this.id(),
                userId = this.userId,
                targetId = this.targetId,
                targetType = this.targetType,
                time = this.time,
                isGotExtraReward = this.isGotExtraReward
            )
        }
    }
}