package com.setyongr.data.remote.mapper

import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.Order
import javax.inject.Inject

class OrderRemoteMapper @Inject constructor(
        private val pinjamanRemoteMapper: PinjamanRemoteMapper,
        private val userRemoteMapper: UserRemoteMapper
) : Mapper<ResponseModel.Order, Order> {
    override fun mapFromEntity(type: ResponseModel.Order): Order {
        return Order(
                type.id,
                userRemoteMapper.mapFromEntity(type.user),
                pinjamanRemoteMapper.mapFromEntity(type.pinjam),
                type.message,
                type.status,
                type.crated_at,
                type.updated_at,
                type.used_at,
                type.finished_at
        )
    }

    override fun mapToEntity(type: Order): ResponseModel.Order {
        return ResponseModel.Order(
                type.id,
                userRemoteMapper.mapToEntity(type.user),
                pinjamanRemoteMapper.mapToEntity(type.pinjam),
                type.message,
                type.status,
                type.crated_at,
                type.updated_at,
                type.used_at,
                type.finished_at
        )
    }

}