package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.entity.VisitId
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.persistence.repository.ActivityRepository
import ru.sigma.hse.business.bot.persistence.repository.CompanyRepository
import ru.sigma.hse.business.bot.persistence.repository.UserRepository
import ru.sigma.hse.business.bot.persistence.repository.VisitRepository

@Component
class JdbcVisitStorage(
    private val visitRepository: VisitRepository,
    private val companyRepository: CompanyRepository,
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
) {
    fun addCompanyVisit(userId: Long, visitCode: String): Visit {
        validateUser(userId)
        val company = companyRepository.findByCode(visitCode)
            ?: run {
                logger.warn { "Company with code $visitCode does not exist" }
                throw NoSuchElementException("Company with code $visitCode does not exist")
            }

        if (visitRepository.existsByUserIdAndCode(userId, company.id())) {
            logger.warn { "Visit with code $visitCode already exists for user $userId" }
            throw IllegalArgumentException("Visit with code $visitCode already exists for user $userId")
        }

        val id = VisitId(
            userId = userId,
            targetId = company.id(),
            targetType = VisitTarget.COMPANY
        )

        val visitEntity = visitRepository.save(
            VisitEntity(
                id = id,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added company visit for user $userId to company ${company.name}" }

        return visitEntity.toVisit()
    }

    fun addActivityVisit(userId: Long, visitCode: String): Visit {
        validateUser(userId)
        val activity = activityRepository.findByCode(visitCode)
            ?: run {
                logger.warn { "Activity with code $visitCode does not exist" }
                throw NoSuchElementException("Activity with code $visitCode does not exist")
            }

        if (visitRepository.existsByUserIdAndCode(userId, activity.id())) {
            logger.warn { "Visit with code $visitCode already exists for user $userId" }
            throw IllegalArgumentException("Visit with code $visitCode already exists for user $userId")
        }

        val id = VisitId(
            userId = userId,
            targetId = activity.id(),
            targetType = VisitTarget.ACTIVITY
        )

        val visitEntity = visitRepository.save(
            VisitEntity(
                id = id,
                time = LocalDateTime.now()
            )
        )

        logger.info { "Added activity visit for user $userId to activity ${activity.name}" }
        return visitEntity.toVisit()
    }

    fun getVisitsByUserId(userId: Long): List<Visit> {
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw NoSuchElementException("User with id $userId does not exist")
        }

        return visitRepository.findByUserId(userId).map {
            it.toVisit()
        }
    }

    private fun validateUser(userId: Long) {
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw NoSuchElementException("User with id $userId does not exist")
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun VisitEntity.toVisit(): Visit {
            return Visit(
                userId = this.id.userId,
                targetId = this.id.targetId,
                targetType = this.id.targetType,
                time = this.time
            )
        }
    }
}