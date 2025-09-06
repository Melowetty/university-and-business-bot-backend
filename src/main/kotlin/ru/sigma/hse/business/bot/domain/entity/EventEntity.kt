package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Entity
import ru.sigma.hse.business.bot.domain.model.EventStatus

@Entity
@Table(name = "event")
class EventEntity(
    @Column(nullable = false)
    var status: EventStatus,

    @Column(nullable = false)
    val answers: List<String>
) : BaseEntity()