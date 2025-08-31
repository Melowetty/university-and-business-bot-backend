package ru.sigma.hse.business.bot.api.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException
import ru.sigma.hse.business.bot.exception.base.NotFoundException

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFound(ex: NotFoundException): ErrorResponse {
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExists(ex: AlreadyExistsException): ErrorResponse {
        return ErrorResponse(
                ex.errorType,
                ex.message
            )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerError(ex: Exception): ErrorResponse {
        return ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                 "Internal server error"
            )
    }

    data class ErrorResponse(
        val errorType: String,
        val message: String,
    )
}