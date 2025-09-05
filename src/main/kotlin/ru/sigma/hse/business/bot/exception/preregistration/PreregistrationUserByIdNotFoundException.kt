package ru.sigma.hse.business.bot.exception.preregistration

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class PreregistrationUserByIdNotFoundException (
    val id: Long
) : NotFoundException("PreregistrationUser", "Preregistration user with id $id not found")