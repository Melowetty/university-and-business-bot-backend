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

    fun existsByTelegramId(
        tgId: Long
    ): Boolean

    fun createUser(
        tgId: Long,
        code: String,
        fullName: String,
        course: Int,
        program: String,
        email: String?
    ): User

    fun updateUser(
        user: User
    ): User

    fun markUserAsCompletedConference(
        userId: Long
    )

    fun deleteUser(
        userId: Long
    )

    fun findUserByCode(code: String): User?

    fun getTelegramIdsByIds(ids: List<Long>): List<Long>

    fun addPointsToUser(userId: Long, points: Int)
}