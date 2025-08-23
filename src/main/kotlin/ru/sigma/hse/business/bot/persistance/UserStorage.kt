package ru.sigma.hse.business.bot.persistance

import ru.sigma.hse.business.bot.domain.model.User

interface UserStorage {
    fun getUser(
        id: Long
    ): User?

    fun createUser(
        name: String,
        middleName: String,
        lastName: String,
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