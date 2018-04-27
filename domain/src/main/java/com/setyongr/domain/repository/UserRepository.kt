package com.setyongr.domain.repository

import com.setyongr.domain.model.*
import io.reactivex.Single
import java.io.File

interface UserRepository {
    fun login(login: Login): Single<Token>
    fun register(register: Register): Single<RegisterResult>
    fun remoteCurrentUser(): Single<User>
    fun currentUser(): Single<User>
    fun updateUser(name: String? = null, email: String? = null, phone: String? = null) : Single<User>
    fun updateUserImage(field: String, file: File) : Single<User>
}