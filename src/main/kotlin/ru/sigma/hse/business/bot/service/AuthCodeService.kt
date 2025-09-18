package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.sigma.hse.business.bot.api.controller.model.CreateAuthCodeRequest
import ru.sigma.hse.business.bot.domain.model.UserRole
import ru.sigma.hse.business.bot.exception.role.RoleByCodeNotFoundException
import ru.sigma.hse.business.bot.persistence.AuthCodeStorage
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator

@Service
class AuthCodeService(
    private val authCodeStorage: AuthCodeStorage,
    private val codeGenerator: CodeGenerator,
    private val userStorage: UserStorage
) {
    fun generateAuthCode(roles: CreateAuthCodeRequest): List<String> {
        val codeList = mutableListOf<String>()

        for (i in 0..(roles.count)){
            val authCode = when (roles.type) {
                UserRole.ADMIN -> authCodeStorage.createAuthCode(codeGenerator.generateAuthCode(), UserRole.ADMIN)
                UserRole.VOLUNTEER -> authCodeStorage.createAuthCode(codeGenerator.generateAuthCode(), UserRole.VOLUNTEER)
                else -> throw IllegalArgumentException("Wrong user role name")
            }
            codeList += authCode.code
        }
        return codeList.toList()
    }

    @Transactional
    fun addRole(userId: Long, code: String) {
        val role = authCodeStorage.getRole(code)
            ?: throw RoleByCodeNotFoundException(code)

        userStorage.addRoleToUser(userId, role, code)
        authCodeStorage.deleteRole(code)
        logger.info { "Add role $role to user with id $userId" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}