package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.entity.VisitId

interface VisitRepository : JpaRepository<VisitEntity, VisitId> {
    fun findByUserId(userId: Long): List<VisitEntity>

    fun findByCompanyId(companyId: Long): List<VisitEntity>

    fun findByActivityId(activityId: Long): List<VisitEntity>
}