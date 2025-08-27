package ru.sigma.hse.business.bot.service.code

import org.springframework.stereotype.Service
import java.security.SecureRandom


private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
private const val CODE_LENGTH = 10
private val random = SecureRandom()

@Service
class CodeGenerator {

    fun generateCompanyCode(): String {
        return "UNC"+generateCode()
    }

    fun generateActivityCode(): String {
        return "UNA"+generateCode()
    }

    fun generateCode(): String {
        return buildString {
            repeat(CODE_LENGTH) {
                append(CHARACTERS[random.nextInt(CHARACTERS.length)])
            }
        }
    }
}