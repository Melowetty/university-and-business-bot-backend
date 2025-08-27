package ru.sigma.hse.business.bot.api.controller.model

import java.time.LocalTime

data class NewActivityRequest(
    val name: String,
    val description: String,
    val location: String
//    , val startTime: LocalTime, val endTime: LocalTime
)