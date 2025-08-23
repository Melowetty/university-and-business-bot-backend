package ru.sigma.hse.business.bot.utils

import java.security.SecureRandom

object CodeGenerator {
    private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    private const val CODE_LENGTH = 10
    private val random = SecureRandom()

    fun generateCode(): String {
        return buildString {
            repeat(CODE_LENGTH) {
                append(CHARACTERS[random.nextInt(CHARACTERS.length)])
            }
        }
    }
}