package com.setyongr.domain.interactor

interface UseCaseOptParam<T, in Params> {
    fun execute(params: Params? = null) : T
}