package com.setyongr.pinjamin.data

import android.content.SharedPreferences
import javax.inject.Inject

class AppTokenProvider @Inject constructor(private val pref: SharedPreferences): TokenProvider {
    private val TOKEN_KEY = "access_token"
    override fun setToken(token: String) {
        pref.edit().putString(TOKEN_KEY, token).apply()
    }
    override fun hasToken(): Boolean = pref.contains(TOKEN_KEY)
    override fun provideToken(): String?  = pref.getString(TOKEN_KEY, null)
    override fun removeToken() {
        pref.edit().remove(TOKEN_KEY).apply()
    }
}