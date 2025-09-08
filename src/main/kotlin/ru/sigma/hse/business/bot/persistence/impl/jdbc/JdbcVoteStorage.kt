package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.VoteEntity
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.exception.vote.VoteByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.VoteRepository

@Component
class JdbcVoteStorage(
    private val voteRepository: VoteRepository
) {
    fun getVote(id: Long): Vote? {
        val vote = voteRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "Vote with id $id does not exist" }
                return null
            }

        logger.info { "Found vote with id $id" }
        return vote.toVote()
    }

    fun createVote(
        eventId: Long,
        answer: String,
        userId: Long
    ): Vote {
        val voteEntity = VoteEntity(
            eventId = eventId,
            answer = answer,
            userId = userId
        )

        val savedEntity = voteRepository.save(voteEntity)
        logger.info { "Created vote with id ${savedEntity.id()}" }
        return savedEntity.toVote()
    }

    fun deleteVote(id: Long) {
        if (!voteRepository.existsById(id)) {
            logger.warn { "Vote with id $id does not exist" }
            throw VoteByIdNotFoundException(id)
        }

        voteRepository.deleteById(id)
        logger.info { "Deleted vote with id $id" }
    }
    
    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun VoteEntity.toVote(): Vote {
            return Vote(
                id = this.id(),
                eventId = eventId,
                answer = answer,
                userId = userId
            )
        }
    }
}