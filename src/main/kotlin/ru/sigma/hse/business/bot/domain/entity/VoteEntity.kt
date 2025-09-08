package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "vote")
class VoteEntity (
    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val answer: String,

    @Column(nullable = false)
    val eventId: Long,
) : BaseEntity()