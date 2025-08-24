package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Visit

interface VisitStorage {
    fun addCompanyVisit(userId: Long, visitCode: String): Visit
    fun addActivityVisit(userId: Long, visitCode: String): Visit
    fun getVisitsByUserId(userId: Long): List<Visit>
}