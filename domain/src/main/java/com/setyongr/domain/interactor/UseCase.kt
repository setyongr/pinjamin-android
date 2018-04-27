package com.setyongr.domain.interactor

interface UseCase<T, in Params> {
    fun execute(params: Params) : T
}