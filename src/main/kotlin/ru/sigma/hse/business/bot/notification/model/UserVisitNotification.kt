package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class UserVisitNotification(
    val visit: DetailedVisit
): SimpleTextNotification {
    override fun getMessageText(): String {
        return "🎊 Ты успешно зарегистрирован на мероприятие *${visit.target.name}*!"
    }
}
