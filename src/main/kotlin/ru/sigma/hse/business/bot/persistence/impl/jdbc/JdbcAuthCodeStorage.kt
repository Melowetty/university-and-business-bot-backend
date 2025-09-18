package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import java.time.Duration
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.ActivityEntity
import ru.sigma.hse.business.bot.domain.entity.ActivityEventEntity
import ru.sigma.hse.business.bot.domain.entity.AuthCodeEntity
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.ActivityEvent
import ru.sigma.hse.business.bot.domain.model.AuthCode
import ru.sigma.hse.business.bot.domain.model.EventStatus
import ru.sigma.hse.business.bot.domain.model.UserRole
import ru.sigma.hse.business.bot.exception.event.EventByIdNotFoundException
import ru.sigma.hse.business.bot.exception.role.RoleByCodeNotFoundException
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.AuthCodeRepository
import ru.sigma.hse.business.bot.persistence.repository.EventRepository

@Component
class JdbcAuthCodeStorage(
    private val authCodeRepository: AuthCodeRepository
) {
    fun createRole(
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

    @Transactional
    fun getRoleAndDelete(code: String): UserRole {
        if (!authCodeRepository.existsByCode(code)) {
            logger.warn { "Auth role with code $code does not exist" }
            throw RoleByCodeNotFoundException(code)
        }
        val authCode = authCodeRepository.findByCode(code)
            ?: throw RoleByCodeNotFoundException(code)
        authCodeRepository.deleteByCode(code)
        logger.info{ "Role with code $code given and deleted" }
        return authCode.role
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