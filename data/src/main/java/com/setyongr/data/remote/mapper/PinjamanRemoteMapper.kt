package com.setyongr.data.remote.mapper

import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.Pinjaman
import javax.inject.Inject

class PinjamanRemoteMapper @Inject constructor(
        private val userRemoteMapper: UserRemoteMapper
): Mapper<ResponseModel.Pinjaman, Pinjaman> {
    override fun mapToEntity(type: Pinjaman): ResponseModel.Pinjaman {
        return ResponseModel.Pinjaman(type.id, type.deskripsi, type.name, type.image, userRemoteMapper.mapToEntity(type.user))
    }

    override fun mapFromEntity(type: ResponseModel.Pinjaman): Pinjaman {
        return Pinjaman(type.id, type.deskripsi, type.name, type.image, userRemoteMapper.mapFromEntity(type.user))
    }

}