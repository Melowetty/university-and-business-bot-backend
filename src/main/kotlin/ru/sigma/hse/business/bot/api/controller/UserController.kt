package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateUserRequest
import ru.sigma.hse.business.bot.api.controller.model.GetUserInfoRequest
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val telegramUserService: TelegramUserService
) {
    @PostMapping()
    fun createUser(@RequestBody newUser: CreateUserRequest): String {
        return userService.createUser(newUser)
    }

    @GetMapping("/{telegramId}")
    fun getUser(@PathVariable telegramId: Long): GetUserInfoRequest {
        val userId = telegramUserService.getUser(telegramId).id
        return userService.getUserInfo(userId)
    }
}