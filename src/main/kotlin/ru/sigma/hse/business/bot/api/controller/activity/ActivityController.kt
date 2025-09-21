package ru.sigma.hse.business.bot.api.controller.activity

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
import ru.sigma.hse.business.bot.api.controller.model.ActivityRequest
import ru.sigma.hse.business.bot.api.controller.model.AddKeyWordAnwerRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateActivityRequest
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.extension.toDto
import ru.sigma.hse.business.bot.service.ActivityService
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.UserService
import ru.sigma.hse.business.bot.service.VisitService

@RestController
@RequestMapping("/activities")
@Tag(name = "Admin: Активности", description = "Управление активностями")
class ActivityController(
    private val activityService: ActivityService,
    private val visitService: VisitService,
    private val  telegramUserService: TelegramUserService
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
        @Parameter(description = "Объект с полной информацией об активности")
        @RequestBody newActivity: CreateActivityRequest
    ): ActivityRequest {
        return activityService.createActivity(newActivity).toDto()
    }

    @GetMapping(
        "/{activityId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить информацию об активности",
        description = "Выдает информацию об активности"
    )
    @ApiResponse(responseCode = "200", description = "Информация об активности")
    fun getActivity(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long
    ): ActivityRequest {
        return activityService.getActivity(activityId).toDto()
    }

    @PostMapping(
        "/{activityId}/visit/{userCode}"
    )
    @Operation(
        summary = "Посетить активность участиком",
        description = "Создание нового посещения активности участником"
    )
    @ApiResponse(responseCode = "200", description = "Новое посещение активности успешно создано")
    fun visitActivity(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long,
        @Parameter(description = "Код пользователя")
        @PathVariable userCode: String
    ) : ActivityRequest {
        return visitService.markUserAsVisitedActivity(activityId, userCode).toDto()
    }

    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить все активности",
        description = "Выдает все активности"
    )
    @ApiResponse(responseCode = "200", description = "Информация об активностях")
    fun getAllActivities(
    ): List<ActivityRequest> {
        return activityService.getAllActivities().map { it.toDto() }.sortedBy { it.startTime }
    }

    @PostMapping(
        "/key-word"
    )
    @Operation(
        summary = "Дать кодовое слово от пользователя",
        description = "Дает ответ пользователя на кодовое слово активности"
    )
    @ApiResponse(responseCode = "200", description = "Ответ принят")
    fun visitActivity(
        @Parameter(description = "Объект с информацией кто и какое слово написал")
        @RequestBody request: AddKeyWordAnwerRequest
    ) {
        val user = telegramUserService.getUser(request.tgId)
        activityService.checkKeyWord(user.id, request.keyWord)
    }
    
    @PostMapping(
        "/{activityId}/visit-copy/{toActivityId}"
    )
    @Operation(
        summary = "Скопировать посещения активности",
        description = "Скопировать посещения активности"
    )
    @ApiResponse(responseCode = "200", description = "Скопированы посещения")
    fun copyVisits(
        @Parameter(description = "ID активности-источника")
        @PathVariable activityId: Long,
        @Parameter(description = "ID активности-цели")
        @PathVariable toActivityId: Long
    ) {
        visitService.copyVisits(activityId, toActivityId)
    }
}