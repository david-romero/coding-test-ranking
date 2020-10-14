package com.idealista.usecases.shared

sealed class Either<out L, out R> {

    data class Left<out L>(val l: L) : Either<L, Nothing>() {
        override fun get(): Nothing = throw UnsupportedOperationException()
    }

    data class Right<out R>(val r: R) : Either<Nothing, R>() {
        override fun get(): R = r
    }

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T {
        return when (this) {
            is Left -> fnL(l)
            is Right -> fnR(r)
        }
    }

    fun <U> map(fn: (R) -> U): Either<L, U> {
        return when (this) {
            is Left -> this
            is Right -> Right(fn(r))
        }
    }

    abstract fun get(): R

    fun isRight(): Boolean = when (this) {
        is Right -> true
        else -> false
    }

}