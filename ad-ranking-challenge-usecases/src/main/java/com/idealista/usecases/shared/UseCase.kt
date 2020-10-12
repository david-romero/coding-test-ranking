package com.idealista.usecases.shared

interface UseCase<U : UseCaseParams, R> {

    fun execute(params: U): Either<Validation, R>

}