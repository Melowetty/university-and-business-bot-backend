package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextNotification

class ConferenceCompleteNotification : SimpleTextNotification {
    override fun getMessageText(): String {
        return "Поздравляем! 🎉" +
                "\n" +
                "Ты посетил достаточно активностей, чтобы получить приз!" +
                "\n" +
                "За своим 🎁 подарком 🎁 можешь подойти" +
                "\n" +
                "к столу волонтеров 😊"
    }
}