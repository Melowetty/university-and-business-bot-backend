package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.persistence.ActivityStorage
import ru.sigma.hse.business.bot.persistence.CompanyStorage
import ru.sigma.hse.business.bot.persistence.VisitStorage

@Service
class VisitService(
    private val visitStorage: VisitStorage,
    private val companyStorage: CompanyStorage,
    private val activityStorage: ActivityStorage
) {
    fun visit(id: String): String {
        // Вызываем старт ивента или компании в боте, а там бот сам будет логику делать
        System.out.println(id)
        var name = "NotFound"
        when (id.substring(0,3)){
            "UNC" -> return "Вы посетили компанию:" + companyStorage.getCompany(
                    visitStorage.addCompanyVisit(1,id).targetId
                )?.name
            "UNA" ->  return "Вы посетили компанию:" + activityStorage.getActivity(
                    visitStorage.addActivityVisit(1,id).targetId
                )?.name
            else -> System.out.println("нет такой компании")
        }
        return "Ошибка чтения кода, попробуйте ещё раз"
    }
}