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
@Tag(name = "Admin: Компании", description = "Управление компаниями")
class CompanyController(
    private val companyService: CompanyService
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить компанию",
        description = "Создает компанию"
    )
    @ApiResponse(responseCode = "200", description = "Компания успешно добавлена")
    fun createCompany(
        @Parameter(description = "Информация о компании для создания")
        @RequestBody newCompany: CreateCompanyRequest
    ): Company {
        return companyService.createCompany(newCompany)
    }

    @GetMapping(
        "/{companyId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить информацию о компании",
        description = "Выдает информацию о компании"
    )
    @ApiResponse(responseCode = "200", description = "Информация о компании")
    fun getCompany(
        @Parameter(description = "ID компании")
        @PathVariable companyId: Long
    ): Company {
        return companyService.getCompany(companyId)
    }
}
