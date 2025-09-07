package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.CompletedUserTaskEntity

@Repository
interface CompletedUserTaskRepository : JpaRepository<CompletedUserTaskEntity, Long> {
    fun findByUserId(userId: Long): List<CompletedUserTaskEntity>
}