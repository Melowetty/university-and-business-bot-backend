package ru.sigma.hse.business.bot.notification.base

interface SimpleTextUrlButtonNotification : Notification {
    fun getMessageText(): String
    fun getButtonText(): String
    fun getButtonUrl(): String
}