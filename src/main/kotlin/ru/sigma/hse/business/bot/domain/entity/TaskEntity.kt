package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Entity
import ru.sigma.hse.business.bot.domain.model.EventStatus

@Entity
@Table(name = "task")
class TaskEntity(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    var isAvailable: Boolean,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val points: Int
) : BaseEntity()