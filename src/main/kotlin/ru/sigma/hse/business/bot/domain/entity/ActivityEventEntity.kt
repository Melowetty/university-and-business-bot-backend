package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Duration
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.persistence.converter.ArrayListConverter

@Entity
@Table(name = "activity_event")
class ActivityEventEntity(
    @Id
    val activityId: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = true)
    val duration: Duration?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: EventStatus,

    @Convert(converter = ArrayListConverter::class)
    @Column(nullable = false, columnDefinition = "TEXT")
    val answers: List<String>,

    @Column(nullable = true)
    val rightAnswer: String?,

    @Column(nullable = false)
    val reward: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActivityEventEntity

        return activityId == other.activityId
    }

    override fun hashCode(): Int {
        return activityId.hashCode()
    }
}