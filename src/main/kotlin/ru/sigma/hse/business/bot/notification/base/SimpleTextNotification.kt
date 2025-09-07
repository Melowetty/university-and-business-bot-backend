package ru.sigma.hse.business.bot.notification.base

interface SimpleTextNotification : Notification {
    /**
     * Returns the text of the notification.
     * @return the text of the notification in Markdown format
     */
    fun getMessageText(): String
}