package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.sigma.hse.business.bot.domain.entity.VisitEntity
import ru.sigma.hse.business.bot.domain.model.VisitTarget

interface VisitRepository : JpaRepository<VisitEntity, Long> {
    @Query("SELECT v FROM VisitEntity v WHERE v.userId = :userId")
    fun findByUserId(
        @Param("userId") userId: Long
    ): List<VisitEntity>

    @Query("SELECT COUNT(v) > 0 FROM VisitEntity v WHERE v.userId = :userId AND v.targetId = :targetId")
    fun existsByUserIdAndCode(
        @Param("userId") userId: Long,
        @Param("targetId") targetId: Long
    ): Boolean

    @Query("SELECT v FROM VisitEntity v WHERE v.targetId = :companyId AND v.targetType = :targetType")
    fun findByCompanyId(
        @Param("companyId") companyId: Long,
        @Param("targetType") targetType: VisitTarget = VisitTarget.COMPANY
    ): List<VisitEntity>

    @Query("SELECT v FROM VisitEntity v WHERE v.targetId = :activityId AND v.targetType = :targetType")
    fun findByActivityId(
        @Param("activityId") activityId: Long,
        @Param("targetType") targetType: VisitTarget = VisitTarget.ACTIVITY
    ): List<VisitEntity>

    @Query("SELECT v FROM VisitEntity v WHERE v.id > :id ORDER BY v.id ASC LIMIT :limit")
    fun findAllByIdGreaterThanOrderByTimeAsc(id: Long, limit: Int): List<VisitEntity>
}