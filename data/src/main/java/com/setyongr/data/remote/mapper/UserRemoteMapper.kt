package com.setyongr.data.remote.mapper

import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.User
import javax.inject.Inject

class UserRemoteMapper @Inject constructor(): Mapper<ResponseModel.User, User> {
    override fun mapToEntity(type: User): ResponseModel.User {
        return ResponseModel.User(type.id, type.username, type.name, type.email, type.noHp, type.ktpImage, type.ktmImage, type.avatar, type.verified, type.point)
    }

    override fun mapFromEntity(type: ResponseModel.User): User {
        return User(type.id, type.username, type.name, type.email, type.noHp, type.ktpImage, type.ktmImage, type.avatar, type.verified, type.point)
    }

}