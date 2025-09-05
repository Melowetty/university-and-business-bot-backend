package ru.sigma.hse.business.bot.api.interceptor

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.sigma.hse.business.bot.utils.LoggingUtils
import ru.sigma.hse.business.bot.utils.RequestIdGenerator

@Component
class LoggingInterceptor: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestIdHeader = request.headerNames.toList().firstOrNull {
            it.lowercase() == REQUEST_ID_HEADER.lowercase()
        }

        val id = request.getHeader(requestIdHeader)
            ?: RequestIdGenerator.generate()

        LoggingUtils.executeWithRequestIdContext(id) {
            filterChain.doFilter(request, response)
        }
    }

    companion object {
        private const val REQUEST_ID_HEADER = "X-Request-ID"
    }
}