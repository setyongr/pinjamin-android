package com.setyongr.domain.interactor

interface TokenProvider {
    fun setToken(token: String)
    fun hasToken(): Boolean
    fun provideToken(): String?
    fun removeToken()
}