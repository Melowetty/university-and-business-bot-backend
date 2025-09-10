package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import ru.sigma.hse.business.bot.domain.model.TaskStatus
import ru.sigma.hse.business.bot.domain.model.TaskType
import java.time.Duration

@Entity
@Table(name = "task")
class TaskEntity(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val type: TaskType,

    @Column(nullable = true)
    var duration: Duration?,

    @Column(nullable = false)
    var status: TaskStatus,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val points: Int
) : BaseEntity()