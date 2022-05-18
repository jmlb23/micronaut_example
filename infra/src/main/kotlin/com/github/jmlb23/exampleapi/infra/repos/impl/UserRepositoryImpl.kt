package com.github.jmlb23.exampleapi.infra.repos.impl

import com.github.jmlb23.exampleapi.domain.bo.*
import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.flatMap
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.fromLeft
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.fromRight
import com.github.jmlb23.exampleapi.domain.repos.UserRepository
import com.github.jmlb23.exampleapi.infra.Database
import com.github.jmlb23.exampleapi.infra.toBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.github.jmlb23.exampleapi.domain.bo.ErrorBO as ErrorBO
import comgithubjmlb23exampleapiinfra.User as EUser
import comgithubjmlb23exampleapiinfra.Grou as EGroup

internal class UserRepositoryImpl(private val db: Database) : UserRepository<ErrorBO> {
    override suspend fun add(user: User): Either<ErrorBO, Int> {
        return withContext(Dispatchers.IO) {
            db.grouQueries.selectGroupByName(user.email.email.split("@").last()).executeAsOneOrNull()?.let {
                db.userQueries.insertUser(
                    id = null,
                    name = user.name.name,
                    surname = user.surname.name,
                    mail = user.email.email,
                    role_id = 2,
                    group_id = it.id
                )
                db.userQueries.selectLastUserRowId().executeAsOne().either { fromRight(it.toInt()) }
            } ?: run {
                db.grouQueries.insertGroup(null, user.email.email.split("@").last())
                val id = db.grouQueries.selectLastGroupRowId().executeAsOne()
                db.userQueries.insertUser(
                    id = null,
                    name = user.name.name,
                    surname = user.surname.name,
                    mail = user.email.email,
                    role_id = 1,
                    group_id = id
                )
                id.either { fromRight(it.toInt()) }
            }
        }

    }

    override suspend fun remove(user: Email): Either<ErrorBO, Int> =
        withContext(Dispatchers.IO) {
            db.userQueries.removeUserByMail(user.email).either { fromRight(1) }
        }


    override suspend fun addMany(user: List<User>): Either<ErrorBO, Int> =
        withContext(Dispatchers.IO) {
            user.map { add(it) }.reduce { acc, either -> acc.flatMap { x -> either.map { x + it } } }
        }

    override suspend fun getOne(user: Email): Either<ErrorBO, User> =
        withContext(Dispatchers.IO) {
            db.userQueries.selectUserByMail(user.email).executeAsOneOrNull().either {
                it?.let { u ->
                    val group = db.grouQueries.selectGroupByName(user.email.split("@").last()).executeAsOne()
                    NonEmptyString(group.name).flatMap {
                        Group(it.name.split("@").last()).flatMap {
                            u.toBO(it)
                        }
                    }
                } ?: fromLeft(RepositoryErrorBO.NotFoundErrorBO)
            }
        }

    override suspend fun getAll(domain: NonEmptyString): Either<ErrorBO, List<User>> =
        withContext(Dispatchers.IO) {
            db.userQueries.selectUsersFromGroup(domain.name).executeAsList().let {
                it.map {
                    Pair(
                        EUser(it.id, it.name, it.surname, it.mail, it.role_id, it.group_id),
                        EGroup(it.group_id, it.name_)
                    )
                }.map { (user, group) ->
                    NonEmptyString(group.name).flatMap {
                        Group(it.name.split("@").last()).flatMap {
                            user.toBO(it)
                        }
                    }
                }
            }
                .fold<Either<ErrorBO, User>, Either<ErrorBO, List<User>>>(fromRight(emptyList())) { acc, either ->
                    either.fold({ fromLeft(it) }) { next -> acc.map { it + next } }
                }.flatMap {
                    it.takeIf { it.isNotEmpty() }?.let { fromRight(it) } ?: fromLeft(RepositoryErrorBO.NotFoundErrorBO)
                }
        }
}
