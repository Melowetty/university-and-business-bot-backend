package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var middleName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false)
    var course: Int,

    @Column(nullable = false)
    var program: String,

    @Column(nullable = true)
    var email: String?
) : BaseEntity()