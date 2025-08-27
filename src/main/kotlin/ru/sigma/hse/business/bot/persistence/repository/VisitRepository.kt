package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.entity.VisitId
import ru.sigma.hse.business.bot.domain.model.VisitTarget

interface VisitRepository : JpaRepository<VisitEntity, VisitId> {
    @Query("SELECT v FROM VisitEntity v WHERE v.id.userId = :userId")
    fun findByUserId(
        @Param("userId") userId: Long
    ): List<VisitEntity>

    @Query("SELECT v FROM VisitEntity v WHERE v.id.targetId = :companyId AND v.id.targetType = :targetType")
    fun findByCompanyId(
        @Param("companyId") companyId: Long,
        @Param("targetType") targetType: VisitTarget = VisitTarget.COMPANY
    ): List<VisitEntity>

    @Query("SELECT v FROM VisitEntity v WHERE v.id.targetId = :activityId AND v.id.targetType = :targetType")
    fun findByActivityId(
        @Param("activityId") activityId: Long,
        @Param("targetType") targetType: VisitTarget = VisitTarget.ACTIVITY
    ): List<VisitEntity>

//    fun findByUserId(userId: Long): List<VisitEntity

//    fun findByCompanyId(companyId: Long): List<VisitEntity>

//    fun findByActivityId(activityId: Long): List<VisitEntity>
}