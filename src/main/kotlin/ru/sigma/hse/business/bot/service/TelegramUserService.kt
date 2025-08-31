package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.exception.user.TelegramUserByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.UserRepository

@Service
class TelegramUserService(
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    fun getUser(telegramId: Long): User {
        val userId = userRepository.getUserIdByTelegramId(telegramId)
            ?: throw TelegramUserByIdNotFoundException(telegramId)

        return userService.getUser(userId)
    }
}