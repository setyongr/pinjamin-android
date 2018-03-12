package com.setyongr.pinjamin.data

interface TokenProvider {
    fun setToken(token: String)
    fun hasToken(): Boolean
    fun provideToken(): String?
    fun removeToken()
}