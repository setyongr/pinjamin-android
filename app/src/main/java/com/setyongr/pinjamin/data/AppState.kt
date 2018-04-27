package com.setyongr.pinjamin.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.setyongr.domain.interactor.TokenProvider
import com.setyongr.pinjamin.common.ConfigKey
import com.setyongr.pinjamin.common.ObservableMap
import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.Order
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.model.User
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class AppState @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val tokenProvider: TokenProvider
) {
    val pinjamanList = ObservableMap<Int, Pinjaman>()
    val myPinjaman = ObservableMap<Int, Pinjaman>()
    val myOrder = ObservableMap<Int, Order>()
    val orderToMe = ObservableMap<Int, Order>()

    val latestTime = BehaviorSubject.create<Date>()

    fun isLoggedIn() : Boolean{
        return sharedPreferences.getBoolean(ConfigKey.IS_LOGGED_IN, false)
    }

    fun setLoggedIn(status: Boolean) {
        sharedPreferences.edit().putBoolean(ConfigKey.IS_LOGGED_IN, status).apply()
    }

    fun logOut() {
        tokenProvider.removeToken()
        setLoggedIn(false)
    }

    fun saveUser(user: User) {
        val gson = Gson()
        sharedPreferences.edit().putString(ConfigKey.CURRENT_USER, gson.toJson(user)).apply()
    }

    fun getUser(): User? {
        val gson = Gson()
        val userJson = sharedPreferences.getString(ConfigKey.CURRENT_USER, null)
        return if (userJson == null) {
            null
        } else {
            gson.fromJson(userJson, User::class.java)
        }
    }
}