package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging

import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.AuthCodeEntity
import ru.sigma.hse.business.bot.domain.model.AuthCode
import ru.sigma.hse.business.bot.domain.model.UserRole
import ru.sigma.hse.business.bot.exception.role.RoleByCodeNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.AuthCodeRepository

@Component
class JdbcAuthCodeStorage(
    private val authCodeRepository: AuthCodeRepository
) {
    fun createAuthCode(
        code: String,
        role: UserRole
    ): AuthCode {
        val authCode = AuthCodeEntity(
            code = code,
            role = role
        )
        authCodeRepository.save(authCode).toAuthCode()
        logger.info { "Role $role created with code $code" }
        return authCode.toAuthCode()
    }

    fun getRole(code: String): UserRole {
        if (!authCodeRepository.existsByCode(code)) {
            logger.warn { "Auth role with code $code does not exist" }
            throw RoleByCodeNotFoundException(code)
        }
        val authCode = authCodeRepository.findByCode(code)
            ?: throw RoleByCodeNotFoundException(code)
        return authCode.role
    }

    fun deleteRole(code: String) {
        if (!authCodeRepository.existsByCode(code)) {
            logger.warn { "Auth role with code $code does not exist" }
            throw RoleByCodeNotFoundException(code)
        }
        authCodeRepository.deleteByCode(code)
        logger.info{ "Role with code $code deleted" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        private fun AuthCodeEntity.toAuthCode(): AuthCode {
            return AuthCode(
                code = code,
                role = role
            )
        }
    }
}