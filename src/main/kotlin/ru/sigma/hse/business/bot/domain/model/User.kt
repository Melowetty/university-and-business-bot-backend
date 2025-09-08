package ru.sigma.hse.business.bot.domain.model

data class User(
    val id: Long,
    var tgId: Long,
    var code: String,
    val fullName: String,
    val course: Int,
    val program: String,
    val email: String?,
    val isCompleteConference: Boolean,
    var score: Int
)
