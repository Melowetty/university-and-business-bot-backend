package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.User

interface UserStorage {
    fun getUsers(
        limit: Int = 1000,
        token: Long = 0,
    ): Pageable<User>

    fun getUser(
        id: Long
    ): User?

    fun createUser(
        tgId: Long,
        fullName: String,
        course: Int,
        program: String,
        email: String?
    ): User

    fun updateUser(
        user: User
    ): User

    fun deleteUser(
        userId: Long
    )
}