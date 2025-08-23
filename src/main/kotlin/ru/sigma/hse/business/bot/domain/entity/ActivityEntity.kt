package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "activity")
class ActivityEntity(
    @Column(nullable = false, unique = true, updatable = false)
    val code: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var location: String,

    @Column(nullable = false)
    var startDate: Instant,

    @Column(nullable = false)
    var endDate: Instant
) : BaseEntity()