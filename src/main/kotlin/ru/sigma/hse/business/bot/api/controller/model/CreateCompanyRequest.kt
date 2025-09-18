package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class CreateCompanyRequest(
    @field:NotBlank(message = "Wrong company name format")
    @Schema(description = "Название компании", example = "Ростикс")
    val name: String,
    @field:NotBlank(message = "Wrong company description format")
    @Schema(description = "Описание компании", example = "Вкусная курочка каждый день только у нас")
    val description: String,
    @field:URL(message = "Wrong company vacancies link format")
    @Schema(description = "Ссылка на сайт", example = "https://rabora/rostics")
    val siteUrl: String?
)