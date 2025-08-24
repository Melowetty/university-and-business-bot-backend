package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import ru.sigma.hse.business.bot.domain.model.VisitTarget

@Entity
@Table(name = "visit")
class VisitEntity(
    @EmbeddedId
    val id: VisitId,
    val time: LocalDateTime,
)

data class VisitId(
    val userId: Long,
    val targetId: Long,
    val targetType: VisitTarget
)