package com.setyongr.data.remote.mapper

import com.setyongr.data.remote.models.RequestModel
import com.setyongr.domain.model.Login
import javax.inject.Inject

class LoginRemoteMapper @Inject constructor(): Mapper<RequestModel.Login, Login> {
    override fun mapFromEntity(type: RequestModel.Login): Login {
        return Login(type.username, type.password)
    }

    override fun mapToEntity(type: Login): RequestModel.Login {
        return RequestModel.Login(type.username, type.password)
    }

}