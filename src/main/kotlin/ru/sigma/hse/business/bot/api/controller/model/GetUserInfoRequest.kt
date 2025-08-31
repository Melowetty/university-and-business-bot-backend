package ru.sigma.hse.business.bot.api.controller.model

class GetUserInfoRequest(
    val userId: Long,
    val name: String,
    val companyCount: Int,
    val activityCount: Int,
    val scoreCount: Int
    //.... other fields
)