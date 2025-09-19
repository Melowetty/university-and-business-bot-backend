package ru.sigma.hse.business.bot.persistence.impl

import java.time.Duration
import java.time.LocalTime
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.model.*
import ru.sigma.hse.business.bot.persistence.Storage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcActivityStorage
import ru.sigma.hse.business.bot.persistence.impl.jdbc.JdbcAuthCodeStorage
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
    private val jdbcTaskStorage: JdbcTaskStorage,
    private val jdbcAuthCodeStorage: JdbcAuthCodeStorage,
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

    override fun addPointsToUser(userId: Long, points: Int) {
        return jdbcUserStorage.addPointsToUser(userId, points)
    }

    override fun addRoleToUser(userId: Long, role: UserRole, code: String) {
        return jdbcUserStorage.addRoleToUser(userId, role, code)
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

    override fun getTelegramIdsByIds(ids: List<Long>): List<Long> {
        return jdbcUserStorage.getTelegramIdsByIds(ids)
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
        siteUrl: String?
    ): Company {
        return jdbcCompanyStorage.createCompany(code, name, description, siteUrl)
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
        type: ActivityType,
        location: String,
        startTime: LocalTime,
        endTime: LocalTime,
        keyWord: String?,
        points: Int
    ): Activity {
        return jdbcActivityStorage.createActivity(code, name, description, type, location, startTime, endTime, keyWord, points)
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

    override fun getVisitsByTargetId(
        targetId: Long,
        limit: Int,
        token: Long
    ): Pageable<Visit> {
        return jdbcVisitStorage.getVisitsByTargetId(targetId, limit, token)
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

    override fun getAllActivities(): List<Activity> {
        return jdbcActivityStorage.getAllActivities()
    }

    override fun getActivityByKeyWord(keyWord: String): Activity {
        return jdbcActivityStorage.getActivityByKeyWord(keyWord)
    }

    override fun getVisitsByUserId(userId: Long): List<Visit> {
        return jdbcVisitStorage.getVisitsByUserId(userId)
    }

    override fun getCountVisitsByUserId(userId: Long): Long {
        return jdbcVisitStorage.getVisitsCountByUserId(userId)
    }

    override fun getVisitByUserIdTargetId(userid: Long, activityId: Long): Visit {
        return jdbcVisitStorage.getVisitByUserIdTargetId(userid, activityId)
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

    override fun getEvent(activityId: Long): ActivityEvent? {
        return jdbcEventStorage.getEvent(activityId)
    }

    override fun createEvent(
        activityId: Long,
        name: String,
        description: String,
        duration: Duration?,
        answers: List<String>,
        rightAnswer: String?,
        reward: Int
    ): ActivityEvent {
        return jdbcEventStorage.createEvent(
            activityId,
            name,
            description,
            duration,
            answers,
            rightAnswer,
            reward,
        )
    }

    override fun updateEventStatus(activityId: Long, status: EventStatus): ActivityEvent {
        return jdbcEventStorage.updateEventStatus(activityId, status)
    }

    override fun deleteEvent(activityId: Long) {
        return jdbcEventStorage.deleteEvent(activityId)
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

    override fun getRanTasks(): List<Task> {
        return jdbcTaskStorage.getRanTasks()
    }

    override fun getTask(id: Long): Task? {
        return jdbcTaskStorage.getTask(id)
    }

    override fun createTask(name: String, type: TaskType, description: String, points: Int, duration: Duration?): Task {
        return jdbcTaskStorage.createTask(name, type, description, points, duration)
    }

    override fun updateTaskStatus(id: Long, status: TaskStatus): Task {
        return jdbcTaskStorage.updateTaskStatus(id, status)
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

    override fun existUserCompleteActivity(userId: Long, taskId: Long): Boolean {
        return jdbcCompletedUserTaskStorage.getByUserIdTaskId(userId, taskId)
    }

    override fun createAuthCode(code: String, role: UserRole): AuthCode {
        return jdbcAuthCodeStorage.createAuthCode(code, role)
    }

    override fun deleteRole(code: String) {
        jdbcAuthCodeStorage.deleteRole(code)
    }

    override fun getRole(code: String): UserRole? {
        return jdbcAuthCodeStorage.getRole(code)
    }

}