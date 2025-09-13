package ru.sigma.hse.business.bot.exception.completed

import ru.sigma.hse.business.bot.exception.base.AlreadyExistsException

class AlreadyExistsCompletedUserTaskException(
    userId: Long,
    taskId: Long
) : AlreadyExistsException("CompletedUserTask", "User with id $userId already complete task with id $taskId")