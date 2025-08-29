package ru.sigma.hse.business.bot.utils

import ru.sigma.hse.business.bot.domain.model.Pageable

object Paginator {
    fun <T> fetchPageable(
        limit: Int = 100,
        startToken: Long = 0,
        fetchFunction: (limit: Int, token: Long) -> Pageable<T>,
        processFunction: (List<T>) -> Unit,
    ) {
        var token = startToken

        while (true) {
            val page = fetchFunction(limit, token)
            processFunction(page.data)

            if (page.data.size < limit) {
                break
            }
            token = page.nextToken
        }
    }
}