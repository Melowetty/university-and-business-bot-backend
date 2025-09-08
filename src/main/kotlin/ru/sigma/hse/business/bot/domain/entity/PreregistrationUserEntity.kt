package ru.sigma.hse.business.bot.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "preregistration_user")
class PreregistrationUserEntity(
    @Id
    var tgId: Long,
)