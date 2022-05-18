package com.github.jmlb23.exampleapi.domain.bo

import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.flatMap

class User private constructor(
    val name: NonEmptyString,
    val surname: NonEmptyString,
    val email: Email,
    val group: Group,
    val boss: Boolean
) {
    companion object {
        operator fun invoke(name: String, surname: String, email: String): Either<ErrorBO, User> =
            NonEmptyString(name).flatMap { name ->
                NonEmptyString(surname).flatMap { surname ->
                    Email(email).flatMap { email ->
                        Group(email.email.split("@").last()).map {
                            User(name, surname, email, it, false)
                        }
                    }
                }
            }

        operator fun invoke(
            name: String,
            surname: String,
            email: String,
            group: String,
            boss: Boolean
        ): Either<ErrorBO, User> =
            NonEmptyString(name).flatMap { name ->
                NonEmptyString(surname).flatMap { surname ->
                    Email(email).flatMap { email ->
                        Group(group).map {
                            User(name, surname, email, it, boss)
                        }
                    }
                }
            }
    }
}

@JvmInline
value class Group private constructor(val name: String) {
    companion object {
        operator fun invoke(name: String): Either<EmailErrorBO, Group> = name.either {
            when {
                it.isEmpty() -> fromLeft(EmailErrorBO.EmptyEmailErrorBO)
                else -> fromRight(Group(name))
            }
        }
    }
}
