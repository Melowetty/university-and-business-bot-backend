package ru.sigma.hse.business.bot.persistence

interface Storage : UserStorage, CompanyStorage, ActivityStorage, VisitStorage, EventStorage, VoteStorage, TaskStorage, PreregistrationUserStorage, CompletedUserTaskStorage, AuthCodeStorage
