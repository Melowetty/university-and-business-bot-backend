package ru.sigma.hse.business.bot.exception.company

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class CompanyByIdNotFoundException(
    companyId: Long,
) : NotFoundException(
    "Company",
    "Company with id $companyId not found"
)