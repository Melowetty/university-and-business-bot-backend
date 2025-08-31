package ru.sigma.hse.business.bot.exception.user

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class UserAlreadyExistsByTelegramIdException(
    telegramId: Long,
) : AlreadyExistsException("User", "User with telegramId $telegramId already exists")