package ru.sigma.hse.business.bot.api.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class UpdateCompanyRequest(
    @field:NotBlank(message = "Wrong company name format")
    @Schema(description = "Company name", example = "Rostics")
    val name: String,
    @field:NotBlank(message = "Wrong company description format")
    @Schema(description = "Company description", example = "Tasty chicken every day")
    val description: String,
    @field:URL(message = "Wrong company vacancies link format")
    @Schema(description = "Company site URL", example = "https://example.com")
    val siteUrl: String?
)
