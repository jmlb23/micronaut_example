package com.github.jmlb23.exampleapi.infra

import com.github.jmlb23.exampleapi.domain.bo.ErrorBO
import com.github.jmlb23.exampleapi.domain.repos.UserRepository
import com.github.jmlb23.exampleapi.domain.services.UserService
import com.github.jmlb23.exampleapi.infra.repos.impl.UserRepositoryImpl
import com.github.jmlb23.exampleapi.infra.services.impl.UserServiceImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

fun getDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
    Database.Schema.create(this)
}

fun getUserRepo(db: Database): UserRepository<ErrorBO> = UserRepositoryImpl(db)

fun getUserService(repo: UserRepository<ErrorBO>): UserService = UserServiceImpl(repo)