package com.setyongr.data

import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.LoginRemoteMapper
import com.setyongr.data.remote.mapper.UserRemoteMapper
import com.setyongr.data.remote.models.RequestModel
import com.setyongr.domain.model.*
import com.setyongr.domain.repository.UserRepository
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

class UserDataRepository @Inject constructor(
        private val pinjaminService: PinjaminService,
        private val userRemoteMapper: UserRemoteMapper,
        private val loginRemoteMapper: LoginRemoteMapper
): UserRepository {
    override fun login(login: Login): Single<Token> =
        pinjaminService.login(loginRemoteMapper.mapToEntity(login))
                .map { Token(it.token) }


    override fun register(register: Register): Single<RegisterResult> =
            pinjaminService.register(
                    req = RequestModel.Register(
                            name = register.name,
                            username = register.username,
                            email = register.email,
                            password = register.password
                    )
            ).map {
                RegisterResult(
                        id = it.id,
                        username = it.username,
                        email = it.email
                )
            }

    override fun currentUser(): Single<User> {
        return Single.error(Throwable("Not implemented"))
    }

    override fun remoteCurrentUser(): Single<User> = pinjaminService.me()
            .map {
                userRemoteMapper.mapFromEntity(it)
            }

    override fun updateUser(name: String?, email: String?, phone: String?): Single<User> {
        return pinjaminService.updateMe(RequestModel.User(
                name = name,
                email = email,
                noHp = phone
        )).map {
            userRemoteMapper.mapFromEntity(it)
        }
    }

    override fun updateUserImage(field: String, file: File): Single<User> {
        val i = file.name.lastIndexOf('.')
        val ext = file.name.substring(i+1)
        val requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                file
        )
        // Create request body for image with random filename without extension
        val imagePart = MultipartBody.Part.createFormData(field, UUID.randomUUID().toString() + '.' + ext, requestFile)
        return pinjaminService.updateMeImage(imagePart)
                .map {
                    userRemoteMapper.mapFromEntity(it)
                }
    }
}