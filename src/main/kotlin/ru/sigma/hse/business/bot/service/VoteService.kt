package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.persistence.VoteStorage

@Service
class VoteService(
    private val voteStorage: VoteStorage,
) {
    fun addAnswer(activityId: Long, userId: Long, answer: String): Vote {
        // TODO: validate answer
        return voteStorage.createVote(activityId, answer, userId)
    }
}