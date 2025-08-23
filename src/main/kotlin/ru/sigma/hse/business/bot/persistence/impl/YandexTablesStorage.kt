package ru.sigma.hse.business.bot.persistence.impl

import org.springframework.stereotype.Component
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.domain.entity.UserEntity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.User

@Component
class YandexTablesStorage {
    fun getAllUsers(): List<UserEntity> {
        return emptyList()
    }

    fun getAllCompanies(): List<CompanyEntity> {
        return emptyList()
    }

    fun addUser(entity: UserEntity) {
        // TODO("Not yet implemented")
    }

    fun updateUser(user: User) {
        //TODO("Not yet implemented")
    }

    fun deleteUser(userId: Long) {
        //TODO("Not yet implemented")
    }

    fun getCompany(id: Long): Company? {
        //TODO("Not yet implemented")
        return null
    }

    fun addCompany(entity: CompanyEntity) {
        //TODO("Not yet implemented")
    }

    fun updateCompany(company: Company) {
        //TODO("Not yet implemented")
    }

    fun deleteCompany(id: Long) {
        //TODO("Not yet implemented")
    }

    fun getLastUserId(): Long {
        return 1
    }

    fun getLastCompanyId(): Long {
        return 1
    }
}