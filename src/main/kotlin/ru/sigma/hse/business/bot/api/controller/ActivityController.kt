package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateActivityRequest
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.service.ActivityService

@RestController
@RequestMapping("/activities")
@Tag(name = "Активности", description = "Управление активностями")
class ActivityController(
    private val activityService: ActivityService
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить новую активность",
        description = "Создание новой активности"
    )
    @ApiResponse(responseCode = "200", description = "Новая активнсоть успешно создана")
    fun createActivity(
        @Parameter(description = "Объект с информацией об активности")
        @RequestBody newActivity: CreateActivityRequest
    ): Activity {
        return activityService.createActivity(newActivity)
    }
}