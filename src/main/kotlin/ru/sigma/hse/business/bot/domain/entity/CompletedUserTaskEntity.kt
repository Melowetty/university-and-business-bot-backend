package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "completed_user_task")
class CompletedUserTaskEntity(
    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val taskId: Long,

    @Column(nullable = false)
    val completeTime: LocalDateTime

) : BaseEntity()