package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class CreateCompanyRequest(
    @Schema(description = "Название компании", example = "Ростикс")
    val name: String,
    @Schema(description = "Описание компании", example = "Вкусная курочка каждый день только у нас")
    val description: String,
    @Schema(description = "Ссылка на вакансию", example = "https://rabora/rostics")
    val vacanciesLink: String
)