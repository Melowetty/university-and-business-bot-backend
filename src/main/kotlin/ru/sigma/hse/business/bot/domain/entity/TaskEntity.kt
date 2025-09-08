package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

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