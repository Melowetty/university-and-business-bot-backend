package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextButtonNotification

class BeRealStartedNotification(
    val taskId: Long,
    val description: String
) : SimpleTextButtonNotification {
    override fun getMessageText(): String {
        return "BeReal!\n\nСуть задания:\n\n$description\n\nНажмите на кнопку ниже, чтобы отправить решение. У вас есть 5 минут!"
    }

    override fun getButtonText(): String {
        return "Отправить ответ ⚡️"
    }

    override fun getButtonCallback(): String {
        return "be_real_start:$taskId"
    }
}