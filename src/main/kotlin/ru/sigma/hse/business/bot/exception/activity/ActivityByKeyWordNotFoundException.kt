package ru.sigma.hse.business.bot.exception.activity

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class ActivityByKeyWordNotFoundException(
    keyWord: String,
) : NotFoundException(
    "Activity",
    "Activity with key word $keyWord not found"
)