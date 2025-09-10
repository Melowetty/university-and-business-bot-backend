package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDateTime
import ru.sigma.hse.business.bot.domain.model.VisitTarget

@Entity
@Table(name = "visit")
class VisitEntity(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "target_id", nullable = false)
    val targetId: Long,

    @Column(name = "target_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val targetType: VisitTarget,

//    @Column(name = "is_got_extra_reward", nullable = false)
//    val isGotExtraReward: Boolean,

    @Column(updatable = false, nullable = false)
    val time: LocalDateTime,
) : BaseEntity()