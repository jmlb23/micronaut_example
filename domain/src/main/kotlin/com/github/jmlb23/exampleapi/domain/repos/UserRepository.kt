package com.github.jmlb23.exampleapi.domain.repos

import com.github.jmlb23.exampleapi.domain.bo.Email
import com.github.jmlb23.exampleapi.domain.bo.NonEmptyString
import com.github.jmlb23.exampleapi.domain.bo.User
import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.bo.ErrorBO as ErrorBO


interface UserRepository<out E : ErrorBO> {
    suspend fun add(user: User): Either<E, Int>
    suspend fun remove(user: Email): Either<E, Int>
    suspend fun addMany(user: List<User>): Either<E, Int>
    suspend fun getOne(user: Email): Either<E, User>
    suspend fun getAll(domain: NonEmptyString): Either<E, List<User>>
}