package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.persistence.VoteStorage

@Service
class VoteService(
    private val voteStorage: VoteStorage,
    private val userService: UserService,
) {

    @Transactional
    fun addAnswer(activityId: Long, userId: Long, answer: String): Vote {
        val user = userService.getUser(userId)
        val votePoints = 5
        userService.addPointsToUserScore(user.id, votePoints)

        // TODO: validate answer
        return voteStorage.createVote(activityId, answer, userId)
    }
}