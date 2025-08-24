package ru.sigma.hse.business.bot.utils

import org.slf4j.MDC

object LoggingUtils {
    private const val LOGGING_REQUEST_ID_KEY = "request_id"

    fun executeWithRequestIdContext(requestId: String, block: () -> Unit) {
        try {
            MDC.put(LOGGING_REQUEST_ID_KEY, requestId)
            return block()
        } finally {
            MDC.clear()
        }
    }
}