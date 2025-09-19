package ru.sigma.hse.business.bot.persistence.impl.jdbc

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.UserEntity
import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.UserRole
import ru.sigma.hse.business.bot.exception.user.UserByIdNotFoundException
import ru.sigma.hse.business.bot.persistence.repository.UserRepository

@Component
class JdbcUserStorage(
    private val userRepository: UserRepository,
) {
    fun getUsers(
        limit: Int,
        token: Long,
    ): Pageable<User> {
        val users = userRepository.findAllByIdGreaterThanOrderByIdAsc(token, limit)
            .map { it.toUser() }

        val nextToken = if (users.size < limit) {
            0L
        } else {
            users.last().id
        }

        logger.info { "Fetched ${users.size} users starting from token $token" }
        return Pageable(
            data = users,
            nextToken = nextToken
        )
    }

    fun existsByTelegramId(telegramId: Long): Boolean {
        return userRepository.existsByTelegramId(telegramId)
    }

    fun getUser(id: Long): User? {
        val user = userRepository.findById(id).getOrNull()
            ?: run {
                logger.warn { "User with id $id does not exist" }
                return null
            }

        logger.info { "Found user with id $id" }
        return user.toUser()
    }

    fun createUser(
        tgId: Long,
        code: String,
        fullName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        val userEntity = userRepository.save(
            UserEntity(
                tgId = tgId,
                code = code,
                role = UserRole.USER,
                authCode = null,
                fullName = fullName,
                course = course,
                program = program,
                email = email,
                creationDate = LocalDateTime.now()
            )
        )

        logger.info { "Created user with id ${userEntity.id()}" }
        return userEntity.toUser()
    }

    fun updateUser(user: User): User {
        if (!userRepository.existsById(user.id)) {
            logger.warn { "User with id ${user.id} does not exist" }
            throw UserByIdNotFoundException(user.id)
        }

        val existingUser = userRepository.findById(user.id).get()

        val updatedUser = existingUser.apply {
            fullName = user.fullName
            course = user.course
            program = user.program
            email = user.email
        }

        logger.info { "Updated user with id ${updatedUser.id()}" }
        return userRepository.save(updatedUser).toUser()
    }

    fun deleteUser(userId: Long) {
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        logger.info { "Deleting user with id $userId" }
        userRepository.deleteById(userId)
    }

    fun giveSurveyReward(userId: Long) {
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        userRepository.giveSurveyReward(userId)
        logger.info { "User $userId got survey reward and double own points" }
    }

    fun existsById(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

    fun markUserAsCompletedConference(userId: Long) {
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        userRepository.markUserAsCompleteConference(userId)
    }

    fun getUserByCode(code: String): User? {
         return userRepository.findByCode(code)?.toUser()
    }

    fun getTelegramIdsByIds(userIds: List<Long>): List<Long> {
        return userRepository.findTelegramIdsByIds(userIds)
    }

    fun addPointsToUser(userId: Long, points: Int){
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        return userRepository.addPointsToUser(userId, points)
    }

    fun addRoleToUser(userId: Long, role: UserRole, code: String){
        if (!userRepository.existsById(userId)) {
            logger.warn { "User with id $userId does not exist" }
            throw UserByIdNotFoundException(userId)
        }

        userRepository.addRoleToUser(userId, role ,code)
        logger.info { "User $userId now has $role role with code $code" }
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        fun UserEntity.toUser(): User {
            return User(
                id = this.id(),
                role = this.role,
                code = this.code,
                tgId = this.tgId,
                fullName = this.fullName,
                course = this.course,
                program = this.program,
                email = this.email,
                isCompleteConference = this.isCompleteConference,
                score = this.currentScore,
                isGotSurveyReward = this.isGotSurveyReward
            )
        }
    }
}