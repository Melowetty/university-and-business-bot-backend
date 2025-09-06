package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.api.controller.model.CreateEventRequest
import ru.sigma.hse.business.bot.api.controller.model.CreateVoteRequest
import ru.sigma.hse.business.bot.api.controller.model.UpdateEventStatusRequest
import ru.sigma.hse.business.bot.domain.model.Event
import ru.sigma.hse.business.bot.domain.model.Vote
import ru.sigma.hse.business.bot.service.EventService

@RestController
@RequestMapping("/events")
@Tag(name = "Ивент", description = "Управление голосованием")
class EventController(
    private val eventService: EventService
) {
    @PostMapping(
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить новое голосование",
        description = "Создание нового голосования"
    )
    @ApiResponse(responseCode = "200", description = "Новое голосование успешно создано")
    fun createEvent(
        @Parameter(description = "Объект с полной информацией о голосовании")
        @RequestBody newEvent: CreateEventRequest
    ): Event {
        return eventService.createEvent(newEvent)
    }

    @GetMapping(
        "/{eventId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Получить информацию о голосовании",
        description = "Выдает информацию о голосовании"
    )
    @ApiResponse(responseCode = "200", description = "Информация о голосовании")
    fun getEvent(
        @Parameter(description = "ID голосования")
        @PathVariable eventId: Long
    ): Event {
        return eventService.getEvent(eventId)
    }

    @PatchMapping(
        "/{eventId}",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Обновить статус голосования",
        description = "Изменяет статус голосования"
    )
    @ApiResponse(responseCode = "200", description = "Изменен статус голосования")
    fun updateEventStatus(
        @Parameter(description = "ID голосования")
        @PathVariable eventId: Long,
        @Parameter(description = "Информация о новом статусе голосования")
        @RequestBody newStatus: UpdateEventStatusRequest
    ): Event {
        return eventService.updateEventStatus(eventId, newStatus)
    }

    @PostMapping(
        "/{eventId}/votes",
        produces = ["application/json"]
    )
    @Operation(
        summary = "Добавить новый голос",
        description = "Внесение голоса от пользователя"
    )
    @ApiResponse(responseCode = "200", description = "Новый голос учтён")
    fun addVoteToEvent(
        @Parameter(description = "ID голосования")
        @PathVariable eventId: Long,
        @Parameter(description = "Объект с информацией о голосе")
        @RequestBody newVote: CreateVoteRequest
    ): Vote {
        return eventService.addVote(eventId, newVote)
    }
}