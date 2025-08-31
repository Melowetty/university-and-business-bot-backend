package ru.sigma.hse.business.bot.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.sigma.hse.business.bot.domain.entity.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.id > :id ORDER BY u.id ASC LIMIT :limit")
    fun findAllByIdGreaterThanOrderByIdAsc(id: Long, limit: Int): List<UserEntity>

    @Query("SELECT id FROM UserEntity WHERE tgId = :telegramId")
    fun getUserIdByTelegramId(telegramId: Long): Long
}