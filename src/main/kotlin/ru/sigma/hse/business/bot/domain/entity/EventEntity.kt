package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import ru.sigma.hse.business.bot.domain.model.EventStatus

@Entity
@Table(name = "event")
class EventEntity(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: EventStatus,

    @Column(nullable = false)
    val answers: List<String>,

    @Column(nullable = true)
    val rightAnswer: String?
) : BaseEntity()