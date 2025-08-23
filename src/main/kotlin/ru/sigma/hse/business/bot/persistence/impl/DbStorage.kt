package ru.sigma.hse.business.bot.persistence.impl

import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.persistence.Storage

@Component
class DbStorage(
    private val jdbcUserStorage: JdbcUserStorage,
    private val jdbcCompanyStorage: JdbcCompanyStorage,
) : Storage {
    override fun getUser(id: Long): User? {
        TODO("Not yet implemented")
    }

    override fun createUser(
        name: String,
        middleName: String,
        lastName: String,
        course: Int,
        program: String,
        email: String?
    ): User {
        return jdbcUserStorage.createUser(name, middleName, lastName, course, program, email)
    }

    override fun updateUser(user: User): User {
        return jdbcUserStorage.updateUser(user)
    }

    override fun deleteUser(userId: Long) {
        jdbcUserStorage.deleteUser(userId)
    }

    override fun getCompany(id: Long): Company? {
        return jdbcCompanyStorage.getCompany(id)
    }

    override fun createCompany(
        name: String,
        description: String,
        vacanciesLink: String
    ): Company {
        return jdbcCompanyStorage.createCompany(name, description, vacanciesLink)
    }

    override fun updateCompany(
        company: Company
    ): Company {
        return jdbcCompanyStorage.updateCompany(company)
    }

    override fun deleteCompany(id: Long) {
        jdbcCompanyStorage.deleteCompany(id)
    }
}