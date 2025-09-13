package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.CompletedUserTaskEntity

@Repository
interface CompletedUserTaskRepository : JpaRepository<CompletedUserTaskEntity, Long> {
    fun findByUserId(userId: Long): List<CompletedUserTaskEntity>

    @Query("SELECT COUNT(*) > 0 FROM CompletedUserTaskEntity u WHERE u.userId = :userId AND u.taskId = :taskId")
    fun existByUserIdTaskId(userId: Long, taskId: Long): Boolean
}