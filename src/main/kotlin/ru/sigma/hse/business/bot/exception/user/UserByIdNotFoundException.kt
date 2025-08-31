package ru.sigma.hse.business.bot.exception.user

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class UserByIdNotFoundException(
    val id: Long
) : NotFoundException("User", "User with id $id not found")