package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Vote


interface VoteStorage {
    fun getVote(id: Long): Vote?

    fun createVote(
        eventId: Long,
        answer: String,
        userId: Long
    ): Vote

    fun deleteVote(id: Long)
}