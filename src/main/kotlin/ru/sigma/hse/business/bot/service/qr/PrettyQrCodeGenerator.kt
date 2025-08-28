package ru.sigma.hse.business.bot.service.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Hashtable
import javax.imageio.ImageIO
import org.springframework.stereotype.Component

@Component("prettyQrCodeGenerator")
class PrettyQrCodeGenerator : QrCodeGenerator {
    override fun generateQrCode(data: String): ByteArray {
        return try {
            val qrCodeSize = 300

            val hintMap = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
            hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

            val qrCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap)

            val matrixWidth = bitMatrix.width
            val image = BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB)
            val graphics = image.createGraphics() as Graphics2D

            // Белый фон
            graphics.color = Color.WHITE
            graphics.fillRect(0, 0, matrixWidth, matrixWidth)

            // Основной цвет QR-кода
            graphics.color = Color(51, 102, 153)

            // Рисуем QR-код
            for (i in 0 until matrixWidth) {
                for (j in 0 until matrixWidth) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1)
                    }
                }
            }

            // Добавляем логотип
            addLogoToQr(graphics, matrixWidth)

            graphics.dispose()

            // Сохраняем в ByteArray
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(image, "PNG", outputStream)

            outputStream.toByteArray()

        } catch (e: Exception) {
            throw RuntimeException("Failed to generate QR code", e)
        }
    }

    private fun addLogoToQr(graphics: Graphics2D, qrSize: Int) {
        try {
            val logoFile = File("src/main/resources/logo.png") // Путь к логотипу
            if (logoFile.exists()) {
                val logo = ImageIO.read(logoFile)

                // Масштабируем логотип до 20% от размера QR-кода
                val logoSize = (qrSize * 0.2).toInt()
                val scaledLogo = getScaledImage(logo, logoSize, logoSize)

                // Позиционируем по центру
                val x = (qrSize - scaledLogo.width) / 2
                val y = (qrSize - scaledLogo.height) / 2

                graphics.drawImage(scaledLogo, x, y, null)
            }
        } catch (e: Exception) {
            println("Logo not added: ${e.message}")
        }
    }

    // Функция масштабирования
    private fun getScaledImage(image: BufferedImage, width: Int, height: Int): BufferedImage {
        val imageWidth = image.getWidth()
        val imageHeight = image.getHeight()

        val scaleX = width.toDouble() / imageWidth
        val scaleY = height.toDouble() / imageHeight
        val scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY)
        val bilinearScaleOp = AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR)

        return bilinearScaleOp.filter(image, BufferedImage(width, height, image.getType()))
    }
}