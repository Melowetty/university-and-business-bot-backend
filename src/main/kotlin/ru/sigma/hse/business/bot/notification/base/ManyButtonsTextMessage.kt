package ru.sigma.hse.business.bot.notification.base

interface ManyButtonsTextMessage : Notification {
    fun getTextMessage(): String
    fun getButtons(): List<Button>
}