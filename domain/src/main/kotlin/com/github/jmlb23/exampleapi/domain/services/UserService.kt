package com.github.jmlb23.exampleapi.domain.services

import com.github.jmlb23.exampleapi.domain.bo.NonEmptyString
import com.github.jmlb23.exampleapi.domain.bo.User
import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.bo.ErrorBO as ErrorBO


interface UserService {
    suspend fun inviteUsers(users: List<User>): Either<ErrorBO, Int>
    suspend fun getGroup(domain: NonEmptyString): Either<ErrorBO, List<User>>
    suspend fun addUser(user: User): Either<ErrorBO, Int>
}