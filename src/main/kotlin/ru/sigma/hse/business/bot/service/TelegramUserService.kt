package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.persistence.repository.UserRepository
import ru.sigma.hse.business.bot.domain.model.User

@Service
class TelegramUserService(
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    fun getUser(telegramId: Long): User {
        val userId = userRepository.getUserIdByTelegramId(telegramId)
            ?: throw IllegalArgumentException("User not found")
        return userService.getUser(userId)
    }
}