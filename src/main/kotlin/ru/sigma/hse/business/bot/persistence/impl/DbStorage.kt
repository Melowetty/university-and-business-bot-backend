package ru.sigma.hse.business.bot.persistence.impl

import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.CompletedUserTask
import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.PreregistrationUser
import ru.sigma.hse.business.bot.domain.model.Task
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.UserVisit
import ru.sigma.hse.business.bot.domain.model.Visit
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcActivityStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcCompanyStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcCompletedUserTaskStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcEventStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcPreregistrationUserStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcTaskStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcUserStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcVisitStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcVoteStorage

@Component
class DbStorage(
    private val jdbcUserStorage: JdbcUserStorage,
    private val jdbcCompanyStorage: JdbcCompanyStorage,
    private val jdbcActivityStorage: JdbcActivityStorage,
    private val jdbcVisitStorage: JdbcVisitStorage,
    private val jdbcPreregistrationUserStorage: JdbcPreregistrationUserStorage,
    private val jdbcCompletedUserTaskStorage: JdbcCompletedUserTaskStorage,
    private val jdbcEventStorage: JdbcEventStorage,
    private val jdbcVoteStorage: JdbcVoteStorage,
    private val jdbcTaskStorage: JdbcTaskStorage
) : Storage {
    override fun getUsers(limit: Int, token: Long): Pageable<User> {
        return jdbcUserStorage.getUsers(limit, token)
    }

    override fun getUser(id: Long): User? {
        return jdbcUserStorage.getUser(id)
    }

    override fun existsByTelegramId(tgId: Long): Boolean {
        return jdbcUserStorage.existsByTelegramId(tgId)
    }

    override fun createUser(
        tgId: Long,
        code: String,
        fullName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        return jdbcUserStorage.createUser(tgId, code, fullName, course, program, email)
    }

    override fun updateUser(user: User): User {
        return jdbcUserStorage.updateUser(user)
    }

    override fun markUserAsCompletedConference(userId: Long) {
        return jdbcUserStorage.markUserAsCompletedConference(userId)
    }

    override fun deleteUser(userId: Long) {
        jdbcUserStorage.deleteUser(userId)
    }

    override fun findUserByCode(code: String): User? {
        return jdbcUserStorage.getUserByCode(code)
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

    override fun getCompanyByCode(code: String): Company? {
        return jdbcCompanyStorage.findByCode(code)
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
        endTime: LocalTime,
        eventId: Long?,
        keyWord: String?,
        points: Int
    ): Activity {
        return jdbcActivityStorage.createActivity(code, name, description, location, startTime, endTime, eventId, keyWord, points)
    }

    override fun updateActivity(activity: Activity): Activity {
        return jdbcActivityStorage.updateActivity(activity)
    }

    override fun deleteActivity(id: Long) {
        jdbcActivityStorage.deleteActivity(id)
    }

    override fun getActivityByCode(code: String): Activity? {
        return jdbcActivityStorage.findByCode(code)
    }

    override fun getVisits(limit: Int, token: Long): Pageable<UserVisit> {
        return jdbcVisitStorage.getVisits(limit, token)
    }

    override fun addCompanyVisit(
        userId: Long,
        companyId: Long
    ): Visit {
        return jdbcVisitStorage.addCompanyVisit(userId, companyId)
    }

    override fun addActivityVisit(
        userId: Long,
        activityId: Long
    ): Visit {
        return jdbcVisitStorage.addActivityVisit(userId, activityId)
    }

    override fun getVisitsByUserId(userId: Long): List<Visit> {
        return jdbcVisitStorage.getVisitsByUserId(userId)
    }

    override fun getCountVisitsByUserId(userId: Long): Long {
        return jdbcVisitStorage.getVisitsCountByUserId(userId)
    }

    override fun getPreregistrationUser(tgId: Long): PreregistrationUser? {
        return jdbcPreregistrationUserStorage.getPreregistrationUser(tgId)
    }

    override fun createPreregistrationUser(tgId: Long): PreregistrationUser {
        return jdbcPreregistrationUserStorage.createPreregistrationUser(tgId)
    }

    override fun deletePreregistrationUser(tgId: Long) {
        jdbcPreregistrationUserStorage.deletePreregistrationUser(tgId)
    }

    override fun getEvent(id: Long): Event? {
        return jdbcEventStorage.getEvent(id)
    }

    override fun createEvent(answers: List<String>, rightAnswer: String?): Event {
        return jdbcEventStorage.createEvent(answers, rightAnswer)
    }

    override fun updateEventStatus(id: Long, status: EventStatus): Event {
        return jdbcEventStorage.updateEventStatus(id, status)
    }

    override fun deleteEvent(id: Long) {
        return jdbcEventStorage.deleteEvent(id)
    }

    override fun getVote(id: Long): Vote? {
        return jdbcVoteStorage.getVote(id)
    }

    override fun createVote(eventId: Long, answer: String, userId: Long): Vote {
        return jdbcVoteStorage.createVote(eventId, answer, userId)
    }

    override fun deleteVote(id: Long) {
        return jdbcVoteStorage.deleteVote(id)
    }
    override fun getTask(id: Long): Task? {
        return jdbcTaskStorage.getTask(id)
    }

    override fun createTask(name: String, description: String, points: Int): Task {
        return jdbcTaskStorage.createTask(name, description, points)
    }

    override fun updateTaskStatus(id: Long, isAvailable: Boolean): Task {
        return jdbcTaskStorage.updateTaskStatus(id, isAvailable)
    }

    override fun deleteTask(id: Long) {
        return jdbcTaskStorage.deleteTask(id)
    }

    override fun getCompletedUserTask(id: Long): CompletedUserTask? {
        return jdbcCompletedUserTaskStorage.getCompletedUserTask(id)
    }

    override fun createCompletedUserTask(userId: Long, taskId: Long): CompletedUserTask {
        return jdbcCompletedUserTaskStorage.createCompletedUserTask(userId, taskId)
    }

    override fun deleteCompletedUserTask(id: Long) {
        return jdbcCompletedUserTaskStorage.deleteCompletedUserTask(id)
    }

    override fun getCompletedUserTasksByUserId(userId: Long): List<CompletedUserTask> {
        return jdbcCompletedUserTaskStorage.getCompletedUserTasksByUserId(userId)
    }
}