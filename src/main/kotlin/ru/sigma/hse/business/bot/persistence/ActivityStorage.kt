package ru.sigma.hse.business.bot.persistence

import java.time.LocalTime
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.ActivityType

interface ActivityStorage {
    fun getActivity(id: Long): Activity?

    fun getActivities(ids: List<Long>): List<Activity>

    fun createActivity(
        code: String,
        name: String,
        description: String,
        type: ActivityType,
        location: String,
        startTime: LocalTime,
        endTime: LocalTime,
        keyWord: String?,
        points: Int
    ): Activity

    fun updateActivity(
        activity: Activity
    ): Activity

    fun deleteActivity(id: Long)

    fun getActivityByCode(code: String): Activity?

    fun getAllActivities(): List<Activity>
}