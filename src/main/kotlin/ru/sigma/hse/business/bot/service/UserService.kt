package ru.sigma.hse.business.bot.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.exception.user.UserAlreadyExistsByTelegramIdException
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.job.SendConferenceCompleteNotificationJob
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator
import ru.sigma.hse.business.bot.service.qr.PrettyQrCodeGenerator
import java.time.Duration

@Service
class UserService(
    private val userStorage: UserStorage,
    private val codeGenerator: CodeGenerator,
    private val qrCodeGenerator: PrettyQrCodeGenerator,
    private val scheduleJobService: ScheduleJobService,
) {
    @Value("\${conference.score-for-complete}")
    private var scoreForCompleteConference: Int = 0

    fun getUser(userId: Long): User {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)
        return user
    }

    fun getUserByCode(code: String): User {
        return userStorage.findUserByCode(code)
            ?: throw BadArgumentException("WRONG_USER_CODE", "Wrong user code")
    }

    fun getUserInfo(userId: Long): GetUserInfoRequest {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        return GetUserInfoRequest(
            userId = userId,
            name = user.fullName,
            course = user.course,
            program = user.program,
            email = user.email
        )
    }

    fun createUser(newUser: CreateUserRequest): GetUserInfoRequest {
        if (userStorage.existsByTelegramId(newUser.tgId)) {
            throw UserAlreadyExistsByTelegramIdException(newUser.tgId)
        }
        val code = codeGenerator.generateUserCode()
        val user = userStorage.createUser(
            tgId = newUser.tgId,
            code = code,
            fullName = newUser.fullName,
            course = newUser.course,
            program = newUser.program,
            email = newUser.email,
        )

        return GetUserInfoRequest(
            userId = user.id,
            name = user.fullName,
            course = user.course,
            program = user.program,
            email = user.email
        )
    }

    fun getUserQr(userId: Long): ByteArray {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        val qr = qrCodeGenerator.generateQrCode(user.code,300)
        return qr
    }

    @Transactional
    fun addPointsToUserScore(userId: Long, points: Int) {
        userStorage.addPointsToUser(userId, points)

        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        if (!user.isCompleteConference && user.score >= scoreForCompleteConference) {
            userStorage.markUserAsCompletedConference(userId)

            val payload = mapOf("userId" to userId.toString())
            scheduleJobService.scheduleJob(
                Duration.ofMinutes(1),
                payload,
                SendConferenceCompleteNotificationJob::class.java
            )
        }
    }
}