package ru.sigma.hse.business.bot.notification.model

import ru.sigma.hse.business.bot.domain.model.ActivityEvent
import ru.sigma.hse.business.bot.notification.base.Button
import ru.sigma.hse.business.bot.notification.base.ManyButtonsTextMessage

class ActivityEventNotification(
    val activityEvent: ActivityEvent
) : ManyButtonsTextMessage {
    override fun getTextMessage(): String {
        return "*${activityEvent.name}*\n\n${activityEvent.description}"
    }

    override fun getButtons(): List<Button> {
        return activityEvent.answers.mapIndexed { index, value ->
            Button(value, "event|${activityEvent.id}|${index}")
        }.also {
            it + Button("Пропустить", "event|${activityEvent.id}|skip")
        }
    }
}