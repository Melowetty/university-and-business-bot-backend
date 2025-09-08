package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.domain.entity.EventEntity
import ru.sigma.hse.business.bot.domain.model.EventStatus

@Repository
interface EventRepository : JpaRepository<EventEntity, Long> {


    @Transactional
    @Modifying
    @Query("update EventEntity e set e.status = ?1 where e.id = ?2")
    fun updateEventStatus(status: EventStatus, id: Long)
}