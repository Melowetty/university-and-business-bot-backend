package ru.sigma.hse.business.bot.exception.task

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class CompletedUserTaskByIdNotFoundException(
    id: Long
) : NotFoundException("CompletedUserTask", "Completed user task with id $id not found")