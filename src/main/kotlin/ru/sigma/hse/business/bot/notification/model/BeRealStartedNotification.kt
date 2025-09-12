package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.notification.base.SimpleTextButtonNotification

class BeRealStartedNotification(
    val taskId: Long,
    val description: String,
    private val durationInMinutes: Int,
) : SimpleTextButtonNotification {
    override fun getMessageText(): String {
        return "BeReal!" +
                "\n\n" +
                "Суть задания:" +
                "\n\n" +
                description +
                "\n\n" +
                "Нажмите на кнопку ниже, чтобы отправить решение. " +
                "У вас есть $durationInMinutes минут!"
    }

    override fun getButtonText(): String {
        return "Отправить ответ ⚡️"
    }

    override fun getButtonCallback(): String {
        return "be_real_start:$taskId"
    }
}