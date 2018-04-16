package com.setyongr.pinjamin.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.setyongr.pinjamin.common.ConfigKey
import com.setyongr.pinjamin.common.ObservableMap
import com.setyongr.pinjamin.data.models.ResponseModel
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class AppState @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val tokenProvider: TokenProvider
) {
    val pinjamanList = ObservableMap<Int, ResponseModel.Pinjaman>()
    val myPinjaman = ObservableMap<Int, ResponseModel.Pinjaman>()
    val myOrder = ObservableMap<Int, ResponseModel.Order>()
    val orderToMe = ObservableMap<Int, ResponseModel.Order>()

    val latestTime = BehaviorSubject.create<Date>()

    val latestPage = 0

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

    fun saveUser(user: ResponseModel.User) {
        val gson = Gson()
        sharedPreferences.edit().putString(ConfigKey.CURRENT_USER, gson.toJson(user)).apply()
    }

    fun getUser(): ResponseModel.User? {
        val gson = Gson()
        val userJson = sharedPreferences.getString(ConfigKey.CURRENT_USER, null)
        return if (userJson == null) {
            null
        } else {
            gson.fromJson(userJson, ResponseModel.User::class.java)
        }
    }
}