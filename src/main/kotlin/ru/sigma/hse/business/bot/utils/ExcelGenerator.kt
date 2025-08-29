package ru.sigma.hse.business.bot.utils

import java.time.format.DateTimeFormatter
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import ru.sigma.hse.business.bot.domain.model.Activity
import ru.sigma.hse.business.bot.domain.model.Company
import ru.sigma.hse.business.bot.domain.model.User
import ru.sigma.hse.business.bot.domain.model.UserVisit

class ExcelGenerator {
    private val workbook = WorkbookFactory.create(true)

    private var currentCompaniesRowIndex = 0
    private var currentActivitiesRowIndex = 0
    private var currentUsersRowIndex = 0

    fun addVisits(visits: List<UserVisit>): ExcelGenerator {
        addCompaniesVisits(visits.filter { it.target is Company })
        addActivitiesVisits(visits.filter { it.target is Activity })
        return this
    }

    private fun addCompaniesVisits(companiesVisits: List<UserVisit>): ExcelGenerator {
        val sheet = getOrCreateSheet(COMPANIES_VISITS_SHEET_NAME)

        if (currentCompaniesRowIndex == 0) {
            val header = listOf("ID участника", "Компания", "Время")
            sheet.addHeader(header)
            currentCompaniesRowIndex++
        }

        val data = companiesVisits.map {
            listOf(
                it.userId.toString(),
                it.target.name,
                it.time.format(datePattern) ?: ""
            )
        }

        sheet.addData(data)
        return this
    }

    private fun addActivitiesVisits(activitiesVisits: List<UserVisit>): ExcelGenerator {
        val sheet = getOrCreateSheet(ACTIVITIES_VISITS_SHEET_NAME)

        if (currentActivitiesRowIndex == 0) {
            val header = listOf("ID участника", "Активность", "Время")
            sheet.addHeader(header)
            currentActivitiesRowIndex++
        }

        val data = activitiesVisits.map {
            listOf(
                it.userId.toString(),
                it.target.name,
                it.time.format(datePattern) ?: ""
            )
        }

        currentActivitiesRowIndex += sheet.addData(data)
        return this
    }

    fun addUsers(users: List<User>): ExcelGenerator {
        val sheet = getOrCreateSheet(USERS_SHEET_NAME)

        if (currentUsersRowIndex == 0) {
            val header = listOf("ID", "ФИО", "Курс", "Email")
            sheet.addHeader(header)
            currentUsersRowIndex++
        }
        val data = users.map {
            listOf(
                it.id.toString(),
                it.fullName,
                it.course.toString(),
                it.email ?: ""
            )
        }

        currentUsersRowIndex += sheet.addData(data)
        return this
    }

    fun generate(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)

        return outputStream.toByteArray()
    }

    private fun getOrCreateSheet(name: String): Sheet = workbook.getSheet(name) ?: workbook.createSheet(name)

    companion object {
        private const val COMPANIES_VISITS_SHEET_NAME = "Компании"
        private const val ACTIVITIES_VISITS_SHEET_NAME = "Активности"
        private const val USERS_SHEET_NAME = "Участники"

        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
        private val datePattern = DateTimeFormatter.ofPattern(DATE_FORMAT)

        private fun Sheet.addHeader(header: List<String>) {
            val headerRow = this.createRow(0)
            header.forEachIndexed { index, title ->
                headerRow.createCell(index).setCellValue(title)
            }
        }

        private fun Sheet.addData(data: List<List<String>>): Int {
            data.forEachIndexed { rowIndex, rowData ->
                val row = this.createRow(rowIndex + 1)
                rowData.forEachIndexed { colIndex, cellData ->
                    row.createCell(colIndex).setCellValue(cellData)
                }
            }

            return data.size
        }
    }
}