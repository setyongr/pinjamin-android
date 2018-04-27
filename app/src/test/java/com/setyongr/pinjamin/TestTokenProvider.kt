package com.setyongr.pinjamin

import com.setyongr.domain.interactor.TokenProvider

class TestTokenProvider: TokenProvider {
    override fun setToken(token: String) {
        //
    }

    override fun hasToken(): Boolean {
        return false
    }

    override fun provideToken(): String? {
        return null
    }

    override fun removeToken() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}