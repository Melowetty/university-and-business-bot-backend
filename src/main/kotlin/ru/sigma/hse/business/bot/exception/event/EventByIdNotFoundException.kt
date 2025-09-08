package ru.sigma.hse.business.bot.exception.event

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class EventByIdNotFoundException(
    id: Long
) : NotFoundException("Event", "Event with id $id not found")