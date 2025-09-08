package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.PreregistrationUserEntity
import ru.sigma.hse.business.bot.domain.model.PreregistrationUser
import ru.sigma.hse.business.bot.exception.preregistration.PreregistrationUserByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.PreregistrationUserRepository

@Component
class JdbcPreregistrationUserStorage(
    private val preregistrationUserRepository: PreregistrationUserRepository,
) {
    fun getPreregistrationUser(tgId: Long): PreregistrationUser? {
        val user = preregistrationUserRepository.findById(tgId).getOrNull()
            ?: run {
                logger.warn { "Preregistration user with id $tgId does not exist" }
                return null
            }

        logger.info { "Found preregistration user with id $tgId" }
        return user.toPreregistrationUser()
    }

    fun createPreregistrationUser(tgId: Long): PreregistrationUser {
        val preregistrationUserEntity = preregistrationUserRepository.save(
            PreregistrationUserEntity(
                tgId = tgId
            )
        )

        logger.info { "Created preregistration user with id ${preregistrationUserEntity.tgId}" }
        return preregistrationUserEntity.toPreregistrationUser()
    }

    fun deletePreregistrationUser(tgId: Long) {
        if (!preregistrationUserRepository.existsById(tgId)) {
            logger.warn { "Preregistration user with id $tgId does not exist" }
            throw PreregistrationUserByIdNotFoundException(tgId)
        }

        logger.info { "Deleting preregistration user with id $tgId" }
        preregistrationUserRepository.deleteById(tgId)
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        fun PreregistrationUserEntity.toPreregistrationUser(): PreregistrationUser {
            return PreregistrationUser(
                tgId = this.tgId
            )
        }
    }
}