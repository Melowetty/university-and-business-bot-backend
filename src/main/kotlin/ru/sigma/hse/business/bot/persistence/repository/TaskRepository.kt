package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.domain.entity.TaskEntity
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import java.time.LocalTime

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long> {


    @Transactional
    @Modifying
    @Query("update TaskEntity t set t.status = ?1 where t.id = ?2")
    fun updateTaskStatus(status: TaskStatus, id: Long)

}