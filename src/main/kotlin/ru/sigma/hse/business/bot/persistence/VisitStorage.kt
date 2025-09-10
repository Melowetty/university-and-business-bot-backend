package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit

interface VisitStorage {
    fun getVisits(limit: Int, token: Long = 0): Pageable<UserVisit>
    fun getVisitsByTargetId(targetId: Long, limit: Int, token: Long = 0): Pageable<Visit>
    fun addCompanyVisit(userId: Long, companyId: Long): Visit
    fun addActivityVisit(userId: Long, activityId: Long): Visit
    fun getVisitsByUserId(userId: Long): List<Visit>
    fun getCountVisitsByUserId(userId: Long): Long
}