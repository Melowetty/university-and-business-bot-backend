package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreatePreregistrationUserRequest
import ru.sigma.hse.business.bot.domain.model.PreregistrationUser
import ru.sigma.hse.business.bot.exception.preregistration.PreregistrationUserByIdNotFoundException
import ru.sigma.hse.business.bot.exception.preregistration.PreregistrationUserAlreadyExistsByTelegramIdException
import ru.sigma.hse.business.bot.persistence.PreregistrationUserStorage

@Service
class PreregistrationUserService(
    private val preregistrationUserStorage: PreregistrationUserStorage
) {
    fun getPreregistrationUser(tgId: Long): PreregistrationUser {
        val user = preregistrationUserStorage.getPreregistrationUser(tgId)
            ?: throw PreregistrationUserByIdNotFoundException(tgId)
        return user
    }

    fun createPreregistrationUser(user: CreatePreregistrationUserRequest): PreregistrationUser {
        if (preregistrationUserStorage.getPreregistrationUser(user.tgId)!=null) {
            throw PreregistrationUserAlreadyExistsByTelegramIdException(user.tgId)
        }

        val preregistrationUser = preregistrationUserStorage.createPreregistrationUser(
            tgId = user.tgId
        )

        return preregistrationUser
    }
}