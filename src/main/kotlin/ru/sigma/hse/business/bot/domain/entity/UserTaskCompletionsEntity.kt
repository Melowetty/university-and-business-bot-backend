package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.Entity
import java.time.LocalTime

@Entity
@Table(name = "user_task_completions")
class UserTaskCompletionsEntity(
    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val taskId: Long,

    @Column(nullable = false)
    val timeTaken: LocalTime

) : BaseEntity()