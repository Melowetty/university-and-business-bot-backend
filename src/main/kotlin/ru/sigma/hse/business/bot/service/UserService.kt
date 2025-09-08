package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.exception.user.UserAlreadyExistsByTelegramIdException
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.persistence.impl.LocalFileStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator
import ru.sigma.hse.business.bot.service.qr.PrettyQrCodeGenerator

@Service
class UserService(
    private val userStorage: UserStorage,
    private val codeGenerator: CodeGenerator,
    private val qrCodeGenerator: PrettyQrCodeGenerator,
    private val localFileStorage: LocalFileStorage
) {
    fun getUser(userId: Long): User {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)
        return user
    }

    fun getUserInfo(userId: Long): GetUserInfoRequest {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        return GetUserInfoRequest(
            userId = userId,
            name = user.fullName,
            course = user.course,
            program = user.program,
            email = user.email,
            score = user.score
        )
    }

    fun createUser(newUser: CreateUserRequest): GetUserInfoRequest {
        if (userStorage.existsByTelegramId(newUser.tgId)) {
            throw UserAlreadyExistsByTelegramIdException(newUser.tgId)
        }
        val code = codeGenerator.generateCompanyCode()
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
            email = user.email,
            score = user.score
        )
    }

    fun getUserQr(userId: Long): String {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)
        val qr = qrCodeGenerator.generateQrCode(user.code,300)
        localFileStorage.save("User"+user.fullName,qr)
        return "ok"
    }

    fun addPointsToUserScore(userId: Long, points: Int): GetUserInfoRequest {
        val user = userStorage.getUser(userId)
            ?: throw UserByIdNotFoundException(userId)

        val newUser = user.apply {
            score += points
        }

        userStorage.updateUser(newUser)

        return GetUserInfoRequest(
            userId = userId,
            name = newUser.fullName,
            course = newUser.course,
            program = newUser.program,
            email = newUser.email,
            score = newUser.score
        )
    }
}