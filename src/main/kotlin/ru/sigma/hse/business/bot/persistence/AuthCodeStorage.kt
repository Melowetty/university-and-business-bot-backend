package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.AuthCode
import ru.sigma.hse.business.bot.domain.model.UserRole


interface AuthCodeStorage {
    fun getRole(code: String): UserRole?

    fun getRoleAndDelete(code: String): UserRole?

    fun createRole(
        code: String,
        role: UserRole
    ): AuthCode

    fun deleteRole(code: String)
}