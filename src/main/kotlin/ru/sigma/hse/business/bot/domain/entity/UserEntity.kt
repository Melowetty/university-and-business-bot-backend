package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import ru.sigma.hse.business.bot.domain.model.UserRole
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false, unique = true)
    var tgId: Long,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER,

    @Column(nullable = true)
    var authCode: String?,

    @Column(nullable = false, unique = true, updatable = false)
    var code: String,

    @Column(nullable = false)
    var fullName: String,

    @Column(nullable = false)
    var course: Int,

    @Column(nullable = false)
    var program: String,

    @Column(nullable = true)
    var email: String?,

    @Column(nullable = false)
    var isCompleteConference: Boolean = false,

    @Column(nullable = false, updatable = false)
    val creationDate: LocalDateTime,

    @Column(nullable = false)
    var currentScore: Int = 0
) : BaseEntity()