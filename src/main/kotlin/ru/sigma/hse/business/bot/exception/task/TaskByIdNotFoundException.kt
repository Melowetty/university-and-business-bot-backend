package ru.sigma.hse.business.bot.exception.task

import ru.sigma.hse.business.bot.exception.base.NotFoundException

class TaskByIdNotFoundException(
    id: Long
) : NotFoundException("Task", "Task with id $id not found")