package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.VoteEntity
import ru.sigma.hse.business.bot.domain.model.Vote

@Repository
interface VoteRepository : JpaRepository<VoteEntity, Long> {

    @Query("SELECT COUNT(*) > 0 FROM VoteEntity v WHERE v.userId = :userId AND v.eventId = :eventId")
    fun existByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): Boolean
}