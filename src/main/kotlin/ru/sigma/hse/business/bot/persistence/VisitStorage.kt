package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit

interface VisitStorage {
    fun getVisits(limit: Int, token: Long = 0): Pageable<UserVisit>
    fun addCompanyVisit(userId: Long, visitCode: String): Visit
    fun addActivityVisit(userId: Long, visitCode: String): Visit
    fun getVisitsByUserId(userId: Long): List<Visit>
}