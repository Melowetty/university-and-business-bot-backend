package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.persistence.UserStorage

@Service
class UserService(
    private val userStorage: UserStorage
) {
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