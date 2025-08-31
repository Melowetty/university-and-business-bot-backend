package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateCompanyRequest
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.service.CompanyService

@RestController
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Управление сущностями компаний")
class CompanyController(
    private val companyService: CompanyService
) {
    @PostMapping
    @Operation(
        summary = "Добавить компанию",
        description = "Создает компанию и присваивает ей свой уникальный код."
    )
    @ApiResponse(responseCode = "200", description = "Компания успешно добавлен")
    fun createCompany(
        @Parameter(description = "Json тело компании с информацией")
        @RequestBody newCompany: CreateCompanyRequest
    ): Company {
        return companyService.createCompany(newCompany)
    }

    @GetMapping("/{companyId}")
    @Operation(
        summary = "Получить информацию о компании",
        description = "Выдает всю информаицю о существующей компании по её id"
    )
    @ApiResponse(responseCode = "200", description = "Информация о компании выведена")
    fun getCompany(
        @Parameter(description = "id компании")
        @PathVariable companyId: Long
    ): Company {
        return companyService.getCompany(companyId)
    }
}
