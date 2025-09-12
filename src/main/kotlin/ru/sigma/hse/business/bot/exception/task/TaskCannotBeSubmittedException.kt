package ru.sigma.hse.business.bot.exception.task

import ru.sigma.hse.business.bot.exception.base.BadArgumentException

class TaskCannotBeSubmittedException : BadArgumentException(
    "TASK_CANNOT_BE_SUBMITTED",
    errorMessage = "Задание не может быть выполнено"
)