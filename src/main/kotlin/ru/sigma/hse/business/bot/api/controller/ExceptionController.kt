package ru.sigma.hse.business.bot.api.controller

import io.github.oshai.kotlinlogging.KotlinLogging
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
        logger.warn(ex) { "Handled entity not found exception" }
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExists(ex: AlreadyExistsException): ErrorResponse {
        logger.warn(ex) { "Handled already exist exception" }
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadArgument(ex: BadArgumentException): ErrorResponse {
        logger.warn(ex) { "Handled bad argument exception" }
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler(produces = ["application/json"])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ErrorResponse {
        logger.warn(ex) { "Handled exception" }
        return ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Internal server error"
        )
    }

    @Schema(description = "Ошибка")
    data class ErrorResponse(
        @Schema(description = "Тип ошибки", example = "USER_NOT_FOUND")
        val errorType: String,
        @Schema(description = "Сообщение об ошибке", example = "User with id 1 not found")
        val message: String,
    )

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}