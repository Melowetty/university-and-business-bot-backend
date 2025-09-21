package ru.sigma.hse.business.bot.exception.vote

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class VoteByUserIdEventIdAlreadyExistException(
    userId: Long,
    eventId: Long
) : AlreadyExistsException("Vote", "User $userId has already voted in the event $eventId")