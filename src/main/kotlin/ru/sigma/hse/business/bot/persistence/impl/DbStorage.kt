package ru.sigma.hse.business.bot.persistence.impl

import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcActivityStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcCompanyStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcUserStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcVisitStorage

@Component
class DbStorage(
    private val jdbcUserStorage: JdbcUserStorage,
    private val jdbcCompanyStorage: JdbcCompanyStorage,
    private val jdbcActivityStorage: JdbcActivityStorage,
    private val jdbcVisitStorage: JdbcVisitStorage,
) : Storage {
    override fun getUsers(limit: Int, token: Long): Pageable<User> {
        return jdbcUserStorage.getUsers(limit, token)
    }

    override fun getUser(id: Long): User? {
        return jdbcUserStorage.getUser(id)
    }

    override fun createUser(
        fullName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        return jdbcUserStorage.createUser(fullName, course, program, email)
    }

    override fun updateUser(user: User): User {
        return jdbcUserStorage.updateUser(user)
    }

    override fun deleteUser(userId: Long) {
        jdbcUserStorage.deleteUser(userId)
    }

    override fun getCompany(id: Long): Company? {
        return jdbcCompanyStorage.getCompany(id)
    }

    override fun getCompanies(ids: List<Long>): List<Company> {
        return jdbcCompanyStorage.getCompanies(ids)
    }

    override fun createCompany(
        code: String,
        name: String,
        description: String,
        vacanciesLink: String
    ): Company {
        return jdbcCompanyStorage.createCompany(code, name, description, vacanciesLink)
    }

    override fun updateCompany(
        company: Company
    ): Company {
        return jdbcCompanyStorage.updateCompany(company)
    }

    override fun deleteCompany(id: Long) {
        jdbcCompanyStorage.deleteCompany(id)
    }

    override fun getActivity(id: Long): Activity? {
        return jdbcActivityStorage.getActivity(id)
    }

    override fun getActivities(ids: List<Long>): List<Activity> {
        return jdbcActivityStorage.getActivities(ids)
    }

    override fun createActivity(
        code: String,
        name: String,
        description: String,
        location: String,
        startTime: LocalTime,
        endTime: LocalTime
    ): Activity {
        return jdbcActivityStorage.createActivity(code, name, description, location, startTime, endTime)
    }

    override fun updateActivity(activity: Activity): Activity {
        return jdbcActivityStorage.updateActivity(activity)
    }

    override fun deleteActivity(id: Long) {
        jdbcActivityStorage.deleteActivity(id)
    }

    override fun getVisits(limit: Int, token: Long): Pageable<UserVisit> {
        return jdbcVisitStorage.getVisits(limit, token)
    }

    override fun addCompanyVisit(userId: Long, visitCode: String): Visit {
        return jdbcVisitStorage.addCompanyVisit(userId, visitCode)
    }

    override fun addActivityVisit(userId: Long, visitCode: String): Visit {
        return jdbcVisitStorage.addActivityVisit(userId, visitCode)
    }

    override fun getVisitsByUserId(userId: Long): List<Visit> {
        return jdbcVisitStorage.getVisitsByUserId(userId)
    }
}