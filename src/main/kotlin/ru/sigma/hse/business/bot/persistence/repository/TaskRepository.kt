package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.domain.entity.TaskEntity

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long> {


    @Transactional
    @Modifying
    @Query("update TaskEntity t set t.isAvailable = ?1 where t.id = ?2")
    fun updateTaskStatus(isAvailable: Boolean, id: Long)
}