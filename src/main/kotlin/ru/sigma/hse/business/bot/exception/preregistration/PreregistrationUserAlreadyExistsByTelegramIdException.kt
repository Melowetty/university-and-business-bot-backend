package ru.sigma.hse.business.bot.exception.preregistration

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class PreregistrationUserAlreadyExistsByTelegramIdException(
    telegramId: Long,
) : AlreadyExistsException("PreregistrationUser", "Preregistration user with telegramId $telegramId already exists")