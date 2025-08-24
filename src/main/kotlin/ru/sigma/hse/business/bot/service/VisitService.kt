package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service

@Service
class VisitService(

) {
    fun visitEvent(visitLink: String): String  {
        // Вызываем старт ивента или компании в боте, а там бот сам будет логику делать
        return "Ok"
    }
}