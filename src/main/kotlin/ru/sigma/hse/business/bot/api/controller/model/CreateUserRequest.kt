package ru.sigma.hse.business.bot.api.controller.model

data class CreateUserRequest(
    val tgId: Long,
    val fullName: String,
    val course: Int,
    val program: String,
    val email: String?
)