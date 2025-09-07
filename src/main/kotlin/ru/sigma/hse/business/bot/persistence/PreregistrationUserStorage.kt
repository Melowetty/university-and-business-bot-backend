package ru.sigma.hse.business.bot.persistence

import ru.sigma.hse.business.bot.domain.model.Pageable
import ru.sigma.hse.business.bot.domain.model.PreregistrationUser

interface PreregistrationUserStorage {
    fun getPreregistrationUser(
        tgId: Long
    ): PreregistrationUser?

    fun createPreregistrationUser(
        tgId: Long
    ): PreregistrationUser

    fun deletePreregistrationUser(
        tgId: Long
    )
}