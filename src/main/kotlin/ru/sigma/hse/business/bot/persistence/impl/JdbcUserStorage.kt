package ru.sigma.hse.business.bot.persistence.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.UserEntity
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.persistence.repository.UserRepository

@Component
class JdbcUserStorage(
    private val userRepository: UserRepository,
) {
    fun createUser(
        name: String,
        middleName: String,
        lastName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        val userEntity = userRepository.save(
            UserEntity(
                name = name,
                middleName = middleName,
                lastName = lastName,
                course = course,
                program = program,
                email = email
            )
        )

        logger.info { "Created user with id ${userEntity.id()}" }
        return userEntity.toUser()
    }

    fun updateUser(user: User): User {
        if (!userRepository.existsById(user.id)) {
            logger.warn { "User with id ${user.id} does not exist" }
            throw NoSuchElementException("User with id ${user.id} does not exist")
        }

        val existingUser = userRepository.findById(user.id).get()

        val updatedUser = existingUser.apply {
            name = user.name
            middleName = user.middleName
            lastName = user.lastName
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
            throw NoSuchElementException("User with id $userId does not exist")
        }

        logger.info { "Deleting user with id $userId" }
        userRepository.deleteById(userId)
    }

    companion object {
        private val logger = KotlinLogging.logger {  }

        fun UserEntity.toUser(): User {
            return User(
                id = this.id(),
                name = this.name,
                middleName = this.middleName,
                lastName = this.lastName,
                course = this.course,
                program = this.program,
                email = this.email
            )
        }
    }
}