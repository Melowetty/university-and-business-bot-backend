package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalTime

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
    var startTime: LocalTime,

    @Column(nullable = false)
    var endTime: LocalTime,

    @Column(nullable = true)
    var eventId: Long?,

    @Column(nullable = false)
    var keyWord: String?,

    @Column(nullable = true)
    val points: Int
) : BaseEntity()