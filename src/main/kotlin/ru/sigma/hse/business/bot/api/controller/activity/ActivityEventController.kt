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
import ru.sigma.hse.business.bot.api.controller.model.CreateEventRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateVoteRequest
import ru.sigma.hse.business.bot.api.controller.model.EventChangeStatus
import ru.sigma.hse.business.bot.api.controller.model.ActivityEventRequest
import ru.sigma.hse.business.bot.api.controller.model.UpdateEventStatusRequest
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.extension.toDto
import ru.sigma.hse.business.bot.service.ActivityEventService
import ru.sigma.hse.business.bot.service.TelegramUserService
import ru.sigma.hse.business.bot.service.VoteService

@RestController
@RequestMapping("/activities/{activityId}/event")
@Tag(name = "Admin: Ивенты", description = "Управление ивентами")
class ActivityEventController(
    private val activityEventService: ActivityEventService,
    private val telegramUserService: TelegramUserService,
    private val voteService: VoteService,
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить новый ивент к активности",
        description = "Создание нового ивента для активности"
    )
    @ApiResponse(responseCode = "200", description = "Новый ивент успешно создан")
    fun createEvent(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long,
        @Parameter(description = "Объект с полной информацией об ивенте")
        @RequestBody newEvent: CreateEventRequest
    ): ActivityEventRequest {
        return activityEventService.createEvent(activityId, newEvent).toDto()
    }

    @GetMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить информацию об ивенте",
        description = "Выдает информацию об ивенте"
    )
    @ApiResponse(responseCode = "200", description = "Информация об ивенте")
    fun getEvent(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long,
    ): ActivityEventRequest {
        return activityEventService.getEvent(activityId).toDto()
    }

    @PostMapping(
        "/status",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Обновить статус ивента",
        description = "Изменяет статус ивента"
    )
    @ApiResponse(responseCode = "200", description = "Изменен статус голосования")
    fun updateEventStatus(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long,
        @Parameter(description = "Информация о новом статусе голосования")
        @RequestBody newStatus: UpdateEventStatusRequest
    ) {
        return when (newStatus.status) {
            EventChangeStatus.RUN -> activityEventService.runEvent(activityId)
            EventChangeStatus.END -> activityEventService.endEvent(activityId)
        }
    }

    @PostMapping(
        "/answer",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить ответа пользователя",
        description = "Внесение ответа пользователя"
    )
    @ApiResponse(responseCode = "200", description = "Ответ пользователя учтён")
    fun addAnswerToEvent(
        @Parameter(description = "ID активности")
        @PathVariable activityId: Long,
        @Parameter(description = "Объект с информацией о голосе")
        @RequestBody newVote: CreateVoteRequest
    ): Vote {
        val userId = telegramUserService.getUser(newVote.userTgId).id
        return voteService.addAnswer(activityId, userId, newVote.answer)
    }
}