package ru.sigma.hse.business.bot.persistence

import java.time.LocalTime
import ru.sigma.hse.business.bot.domain.model.Activity

interface ActivityStorage {
    fun getActivity(id: Long): Activity?

    fun createActivity(
        code: String,
        name: String,
        description: String,
        location: String,
        startTime: LocalTime,
        endTime: LocalTime
    ): Activity

    fun updateActivity(
        activity: Activity
    ): Activity

    fun deleteActivity(id: Long)
}