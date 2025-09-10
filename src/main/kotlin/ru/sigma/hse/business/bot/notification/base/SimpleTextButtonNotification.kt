package ru.sigma.hse.business.bot.notification.base

interface SimpleTextButtonNotification : ManyButtonsTextMessage {
    fun getMessageText(): String
    fun getButtonText(): String
    fun getButtonCallback(): String

    override fun getTextMessage(): String = getMessageText()
    override fun getButtons(): List<Button> = listOf(Button(getButtonText(), getButtonCallback()))
}