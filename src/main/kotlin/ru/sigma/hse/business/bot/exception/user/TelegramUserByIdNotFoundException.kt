package ru.sigma.hse.business.bot.exception.user

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class TelegramUserByIdNotFoundException(
    telegramId: Long
) : NotFoundException("User", "User with id $telegramId not found")