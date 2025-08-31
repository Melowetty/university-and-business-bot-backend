package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.exception.visit.VisitAlreadyExistsException
import ru.sigma.hse.business.bot.persistence.repository.VisitRepository

@Component
class JdbcVisitStorage(
    private val visitRepository: VisitRepository,
    private val jdbcCompanyStorage: JdbcCompanyStorage,
    private val jdbcActivityStorage: JdbcActivityStorage,
    private val jdbcUserStorage: JdbcUserStorage,
) {
    fun getVisits(limit: Int, token: Long): Pageable<UserVisit> {
        val visits = visitRepository.findAllByIdGreaterThanOrderByTimeAsc(token, limit)

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

    fun addCompanyVisit(userId: Long, visitCode: String): Visit {
        validateUser(userId)
        val company = jdbcCompanyStorage.findByCode(visitCode)
            ?: run {
                logger.warn { "Company with code $visitCode does not exist" }
                throw NoSuchElementException("Company with code $visitCode does not exist")
            }

        if (visitRepository.existsByUserIdAndCode(userId, company.id)) {
            logger.warn { "Visit with code $visitCode already exists for user $userId" }
            throw VisitAlreadyExistsException(visitCode)
        }

        val visitEntity = visitRepository.save(
            VisitEntity(
                userId = userId,
                targetId = company.id,
                targetType = VisitTarget.COMPANY,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added company visit for user $userId to company ${company.name}" }

        return visitEntity.toVisit()
    }

    fun addActivityVisit(userId: Long, visitCode: String): Visit {
        validateUser(userId)
        val activity = jdbcActivityStorage.findByCode(visitCode)
            ?: run {
                logger.warn { "Activity with code $visitCode does not exist" }
                throw NoSuchElementException("Activity with code $visitCode does not exist")
            }

        if (visitRepository.existsByUserIdAndCode(userId, activity.id)) {
            logger.warn { "Visit with code $visitCode already exists for user $userId" }
            throw VisitAlreadyExistsException(visitCode)
        }

        val visitEntity = visitRepository.save(
            VisitEntity(
                userId = userId,
                targetId = activity.id,
                targetType = VisitTarget.ACTIVITY,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added activity visit for user $userId to activity ${activity.name}" }
        return visitEntity.toVisit()
    }

    fun getVisitsByUserId(userId: Long): List<Visit> {
        if (!jdbcUserStorage.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw NoSuchElementException("User with id $userId does not exist")
        }

        return visitRepository.findByUserId(userId).map {
            it.toVisit()
        }
    }

    fun getVisitsCountByUserId(userId: Long): Long {
        validateUser(userId)
        return visitRepository.countByUserId(userId)
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
                userId = this.userId,
                targetId = this.targetId,
                targetType = this.targetType,
                time = this.time
            )
        }
    }
}