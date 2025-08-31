package ru.sigma.hse.business.bot.domain.model

import io.swagger.v3.oas.annotations.media.Schema

data class Company(
    @Schema(description = "Id клмпании", example = "1")
    override val id: Long,
    @Schema(description = "Название компании", example = "Ростикс")
    override val name: String,
    @Schema(description = "Описание компании", example = "Прикольная компания со вкусной курочкой")
    override val description: String,
    @Schema(description = "Ссылка на вакансию", example = "https://ростикс.ком")
    val vacanciesLink: String
) : Visitable(id, name, description)
