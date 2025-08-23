package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "company")
class CompanyEntity(
    @Column(nullable = false, unique = true, updatable = false)
    val code: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var vacanciesLink: String
) : BaseEntity()
