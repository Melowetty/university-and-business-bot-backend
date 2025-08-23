package ru.sigma.hse.business.bot.domain.entity

data class UserEntity(
    val id: Long,
    val name: String,
    val middleName: String,
    val lastName: String,
    val course: Int,
    val program: String,
    val email: String?
)