package ru.sigma.hse.business.bot.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.CreateRoleRequest
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.UserRole
import ru.sigma.hse.business.bot.exception.role.RoleByCodeNotFoundException
import ru.sigma.hse.business.bot.persistence.AuthCodeStorage
import ru.sigma.hse.business.bot.persistence.UserStorage
import ru.sigma.hse.business.bot.service.code.CodeGenerator

@Service
class AuthCodeService(
    private val roleStorage: AuthCodeStorage,
    private val codeGenerator: CodeGenerator,
    private val userStorage: UserStorage
) {
    fun generateRole(roles: CreateRoleRequest): List<String> {
        val codeList = mutableListOf<String>()

        for (i in 0..(roles.count)){
            val authCode = when (roles.type) {
                UserRole.ADMIN -> roleStorage.createRole(codeGenerator.generateRoleCode(), UserRole.ADMIN)
                UserRole.VOLUNTEER -> roleStorage.createRole(codeGenerator.generateRoleCode(), UserRole.VOLUNTEER)
                else -> throw IllegalArgumentException("Wrong user role name")
            }
            codeList += authCode.code
        }
        return codeList.toList()
    }

    @Transactional
    fun addRole(userId: Long, code: String) {
        val role = roleStorage.getRoleAndDelete(code)
            ?: throw RoleByCodeNotFoundException(code)

        userStorage.addRoleToUser(userId, role, code)
        logger.info { "Add role $role to user with id $userId" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}