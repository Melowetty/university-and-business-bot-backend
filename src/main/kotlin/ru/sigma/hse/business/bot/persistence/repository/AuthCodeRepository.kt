package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.AuthCodeEntity

@Repository
interface AuthCodeRepository : JpaRepository<AuthCodeEntity, Long> {
    @Query("SELECT a FROM AuthCodeEntity a WHERE a.code = :code")
    fun findByCode(code: String): AuthCodeEntity?

    @Modifying
    @Query("DELETE FROM AuthCodeEntity a WHERE a.code = :code")
    fun deleteByCode(code: String)

    @Query("SELECT COUNT(*) > 0 FROM AuthCodeEntity a WHERE a.code = :code")
    fun existsByCode(code: String): Boolean
}