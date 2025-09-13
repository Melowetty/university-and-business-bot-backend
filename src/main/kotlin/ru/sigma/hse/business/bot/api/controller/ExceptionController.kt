package ru.sigma.hse.business.bot.api.controller

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException
import ru.sigma.hse.business.bot.exception.base.BadArgumentException
import ru.sigma.hse.business.bot.exception.base.NotFoundException

@RestControllerAdvice
class ExceptionController {
    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFound(ex: NotFoundException): ErrorResponse {
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExists(ex: AlreadyExistsException): ErrorResponse {
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadArgument(ex: BadArgumentException): ErrorResponse {
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @Schema(description = "Ошибка")
    data class ErrorResponse(
        @Schema(description = "Тип ошибки", example = "USER_NOT_FOUND")
        val errorType: String,
        @Schema(description = "Сообщение об ошибке", example = "User with id 1 not found")
        val message: String,
    )
}