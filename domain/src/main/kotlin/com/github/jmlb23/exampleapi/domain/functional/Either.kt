package com.github.jmlb23.exampleapi.domain.functional

sealed class Either<out L, out R> {
    data class Left<out L>(val left: L) : Either<L, Nothing>()
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    companion object {
        fun <L> fromLeft(left: L): Either<L, Nothing> = Left(left)
        fun <R> fromRight(right: R): Either<Nothing, R> = Right(right)
        inline fun <L, R, R2> Either<L, R>.flatMap(f: (R) -> Either<L, R2>): Either<L, R2> = when (this) {
            is Left -> fromLeft(this.left)
            is Right -> f(this.right)
        }

        fun <T, L, R> T.either(f: Companion.(T) -> Either<L, R>): Either<L, R> = with(Companion) { f(this@either) }

    }

    fun <R2> map(f: (R) -> R2): Either<L, R2> = when (this) {
        is Left -> Left(this.left)
        is Right -> fromRight(f(this.right))
    }

    fun <L2> mapError(f: (L) -> L2): Either<L2, R> = when (this) {
        is Left -> fromLeft(f(this.left))
        is Right -> fromRight(this.right)
    }

    inline fun <T> fold(onLeft: (L) -> T, onRight: (R) -> T): T {
        return when (this) {
            is Left -> onLeft(this.left)
            is Right -> onRight(this.right)
        }
    }

}