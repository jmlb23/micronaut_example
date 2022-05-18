package com.github.jmlb23.exampleapi.domain.bo

import com.github.jmlb23.exampleapi.domain.functional.Either
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.either

@JvmInline
value class NonEmptyString private constructor(val name: String) {
    companion object {
        operator fun invoke(name: String): Either<StringErrorBO, NonEmptyString> = name.either {
            if (it.isEmpty()) fromLeft(StringErrorBO.EmptyStringErrorBO)
            else fromRight(NonEmptyString(name))
        }
    }
}