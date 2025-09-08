package ru.sigma.hse.business.bot.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ArrayListConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return attribute?.joinToString(DELIMITER) ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData?.split(DELIMITER)?.filter { it.isNotBlank() } ?: emptyList()
    }

    companion object {
        private const val DELIMITER = "|"
    }
}