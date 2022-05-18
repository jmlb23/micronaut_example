package com.github.jmlb23.exampleapi.infra.services.impl

import com.github.jmlb23.exampleapi.domain.bo.NonEmptyString
import com.github.jmlb23.exampleapi.domain.bo.User
import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.repos.UserRepository
import com.github.jmlb23.exampleapi.domain.services.UserService
import com.github.jmlb23.exampleapi.domain.bo.ErrorBO as ErrorBO

internal class UserServiceImpl(private val repository: UserRepository<ErrorBO>) : UserService {

    override suspend fun inviteUsers(users: List<User>): Either<ErrorBO, Int> = repository.addMany(users)

    override suspend fun getGroup(domain: NonEmptyString): Either<ErrorBO, List<User>> = repository.getAll(domain)

    override suspend fun addUser(user: User): Either<ErrorBO, Int> = repository.add(user)
}