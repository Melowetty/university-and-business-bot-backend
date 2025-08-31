package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.VisitTarget
import ru.sigma.hse.business.bot.persistence.UserStorage

@Service
class UserService(
    private val userStorage: UserStorage,
    private val visitService: VisitService
) {
    fun getUser(userId: Long): User {
        val user = userStorage.getUser(userId)
            ?: throw IllegalArgumentException("User not found")
        return user
    }

    fun getUserInfo(userId: Long): GetUserInfoRequest {
        val user = userStorage.getUser(userId)
            ?: throw IllegalArgumentException("User not found")
        val allVisitsDetailedInfo = visitService.getUserVisits(userId)
        val companyNumber = allVisitsDetailedInfo.filter {it.type == VisitTarget.COMPANY}.size
        val activityNumber = allVisitsDetailedInfo.filter {it.type == VisitTarget.ACTIVITY}.size
        return GetUserInfoRequest(
            userId = userId,
            name = user.fullName,
            companyCount = companyNumber,
            activityCount = activityNumber,
            scoreCount = activityNumber + companyNumber
        )
    }

    fun createUser(newUser: CreateUserRequest): String {
        userStorage.createUser(
            tgId = newUser.tgId,
            fullName = newUser.fullName,
            course = newUser.course,
            program = newUser.program,
            email = newUser.email
        )
        return "done"
    }
}