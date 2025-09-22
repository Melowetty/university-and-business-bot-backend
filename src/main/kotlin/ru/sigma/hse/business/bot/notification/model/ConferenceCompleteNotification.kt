package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class ConferenceCompleteNotification : SimpleTextNotification {
    override fun getMessageText(): String {
        return "Поздравляем! 🎉" +
                "\n\n" +
                "Ты посетил достаточно активностей, чтобы получить приз! " +
                "За своим подарком 🎁 можешь подойти " +
                "к столу волонтеров 😊"
    }
}