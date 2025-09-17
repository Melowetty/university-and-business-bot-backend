package ru.sigma.hse.business.bot.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.persistence.VoteStorage

@Service
class VoteService(
    private val voteStorage: VoteStorage,
    private val userService: UserService,
    private val eventService: ActivityEventService,
) {

    @Transactional
    fun addAnswer(activityId: Long, userId: Long, answer: String): Vote {
        val user = userService.getUser(userId)
        val event = eventService.getEvent(activityId)

        if (eventService.isCorrectAnswer(activityId, answer)) {
            userService.addPointsToUserScore(user.id, event.reward)
        }

        return voteStorage.createVote(activityId, answer, userId)
    }
}