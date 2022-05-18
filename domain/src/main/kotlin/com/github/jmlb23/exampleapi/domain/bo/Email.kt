package com.github.jmlb23.exampleapi.domain.bo

import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.either

@JvmInline
value class Email private constructor(val email: String) {
    companion object {
        operator fun invoke(email: String): Either<EmailErrorBO, Email> = email.either {
            when {
                it.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$".toRegex()) -> fromRight(
                    Email(email)
                )
                else -> fromLeft(EmailErrorBO.EmptyEmailErrorBO)
            }
        }
    }
}