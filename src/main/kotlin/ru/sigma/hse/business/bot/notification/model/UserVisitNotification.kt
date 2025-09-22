package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.domain.model.DetailedVisit
import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class UserVisitNotification(
    val visit: DetailedVisit
): SimpleTextNotification {
    override fun getMessageText(): String {
        return "🎊 Вы успешно зарегистрированы на мероприятие ${visit.target.name}!"
    }
}
