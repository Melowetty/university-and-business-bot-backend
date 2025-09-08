package ru.sigma.hse.business.bot.exception.vote

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class VoteByIdNotFoundException(
    id: Long
) : NotFoundException("Vote", "Vote with id $id not found")