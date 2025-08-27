package ru.sigma.hse.business.bot.service.qr

interface QrCodeGenerator {
    fun generateQrCode(data: String): ByteArray
}