package com.github.jmlb23.exampleapi.di

import com.github.jmlb23.exampleapi.domain.bo.ErrorBO
import com.github.jmlb23.exampleapi.domain.repos.UserRepository
import com.github.jmlb23.exampleapi.domain.services.UserService
import com.github.jmlb23.exampleapi.infra.Database
import com.github.jmlb23.exampleapi.infra.getDriver
import com.github.jmlb23.exampleapi.infra.getUserRepo
import com.github.jmlb23.exampleapi.infra.getUserService
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Factory
class DI {

    @Singleton
    fun provideRepository(db: Database): UserRepository<ErrorBO> = getUserRepo(db)

    @Singleton
    fun provideDatabase(): Database = Database(getDriver())

    @Singleton
    fun provideService(repository: UserRepository<ErrorBO>): UserService = getUserService(repository)

    @Singleton
    fun provideLogger(): Logger = LoggerFactory.getLogger("MyApp")
}