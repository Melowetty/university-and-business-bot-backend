package ru.sigma.hse.business.bot.exception.role

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class RoleByCodeNotFoundException(
    code: String
) : NotFoundException("AuthCode", "Role with code $code not found")