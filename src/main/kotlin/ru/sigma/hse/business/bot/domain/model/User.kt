package ru.sigma.hse.business.bot.domain.model

data class User(
    val id: Long,
    var tgId: Long,
    val fullName: String,
    val course: Int,
    val program: String,
    val email: String?,
    val isCompleteConference: Boolean,
)
