package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.EventEntity

@Repository
interface EventRepository : JpaRepository<EventEntity, Long> {
}