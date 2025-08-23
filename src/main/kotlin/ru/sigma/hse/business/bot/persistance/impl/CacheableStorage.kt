package ru.sigma.hse.business.bot.persistance.impl

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.domain.entity.UserEntity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.persistance.CompanyStorage
import ru.sigma.hse.business.bot.persistance.UserStorage
import ru.sigma.hse.business.bot.utils.CodeGenerator

@Primary
@Component
class CacheableStorage(
    private val inMemoryStorage: InMemoryStorage,
    private val yandexTablesStorage: YandexTablesStorage
) : UserStorage, CompanyStorage {
    init {
        // Initialize in-memory storage with data from Yandex Tables
        val users = yandexTablesStorage.getAllUsers()
        val companies = yandexTablesStorage.getAllCompanies()
        inMemoryStorage.fillStorage(users, companies)
    }

    override fun getUser(id: Long): User? {
        return inMemoryStorage.getUser(id)?.toUser()
    }

    override fun createUser(
        name: String,
        middleName: String,
        lastName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        synchronized(this) {
            val userEntity = UserEntity(
                id = yandexTablesStorage.getLastUserId() + 1,
                name = name,
                middleName = middleName,
                lastName = lastName,
                course = course,
                program = program,
                email = email
            )

            inMemoryStorage.addUser(userEntity)
            yandexTablesStorage.addUser(userEntity)

            return userEntity.toUser()
        }
    }

    override fun updateUser(user: User): User {
        inMemoryStorage.updateUser(user)
        yandexTablesStorage.updateUser(user)

        return user
    }

    override fun deleteUser(userId: Long) {
        inMemoryStorage.deleteUser(userId)
        yandexTablesStorage.deleteUser(userId)
    }

    override fun getCompany(id: Long): Company? {
        return inMemoryStorage.getCompany(id)?.toCompany()
    }

    override fun createCompany(name: String, description: String, vacanciesLink: String): Company {
        val code = CodeGenerator.generateCode()
        synchronized(this) {
            val companyEntity = CompanyEntity(
                id = yandexTablesStorage.getLastCompanyId() + 1,
                code = code,
                name = name,
                description = description,
                vacanciesLink = vacanciesLink,
            )

            inMemoryStorage.addCompany(companyEntity)
            yandexTablesStorage.addCompany(companyEntity)

            return companyEntity.toCompany()
        }
    }

    override fun updateCompany(company: Company): Company {
        inMemoryStorage.updateCompany(company)
        yandexTablesStorage.updateCompany(company)

        return company
    }

    override fun deleteCompany(id: Long) {
        inMemoryStorage.deleteCompany(id)
        yandexTablesStorage.deleteCompany(id)
    }

    companion object {
        private fun UserEntity.toUser(): User {
            return User(
                id = this.id,
                name = this.name,
                middleName = this.middleName,
                lastName = this.lastName,
                course = this.course,
                program = this.program,
                email = this.email
            )
        }

        private fun CompanyEntity.toCompany(): Company {
            return Company(
                id = this.id,
                name = this.name,
                description = this.description,
                vacanciesLink = this.vacanciesLink
            )
        }
    }

}