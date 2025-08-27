package ru.sigma.hse.business.bot.service.qr

import org.springframework.stereotype.Component

@Component
@PrettyQrCode  // ← Qualifier аннотация
class PrettyQrCodeGenerator : QrCodeGenerator {

    override fun generateQrCode(data: String): ByteArray {
        throw RuntimeException("Failed to generate QR code for data: $data")
    }
}