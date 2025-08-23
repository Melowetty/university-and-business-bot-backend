package ru.sigma.hse.business.bot.persistance.impl

import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.domain.entity.UserEntity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.User

@Component
class InMemoryStorage(
    private val userStorage: HashMap<Long, UserEntity> = hashMapOf(),
    private val companyStorage: HashMap<Long, CompanyEntity> = hashMapOf()
) {
    fun fillStorage(users: List<UserEntity>, companies: List<CompanyEntity>) {
        users.forEach { user ->
            userStorage[user.id] = user
        }

        companies.forEach { company ->
            companyStorage[company.id] = company
        }
    }

    fun getUser(id: Long): UserEntity? {
        return userStorage[id]
    }

    fun addUser(user: UserEntity) {
        userStorage[user.id] = user
    }

    fun updateUser(user: User) {
        val existingUser = userStorage[user.id]
            ?: throw NoSuchElementException("User with id ${user.id} does not exist")

        val newUser = existingUser.copy(
            name = user.name,
            middleName = user.middleName,
            lastName = user.lastName,
            course = user.course,
            program = user.program,
            email = user.email
        )

        userStorage[user.id] = newUser
    }

    fun deleteUser(userId: Long) {
        if (userStorage.containsKey(userId)) {
            userStorage.remove(userId)
        }
    }

    fun getCompany(id: Long): CompanyEntity? {
        return companyStorage[id]
    }

    fun addCompany(entity: CompanyEntity) {
        companyStorage[entity.id] = entity
    }

    fun updateCompany(company: Company) {
        val existingCompany = companyStorage[company.id]
            ?: throw NoSuchElementException("Company with id ${company.id} does not exist")

        val newCompany = existingCompany.copy(
            name = company.name,
            description = company.description,
            vacanciesLink = company.vacanciesLink
        )

        companyStorage[company.id] = newCompany
    }

    fun deleteCompany(id: Long) {
        if (companyStorage.containsKey(id)) {
            companyStorage.remove(id)
        }
    }

}