package com.setyongr.data

import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.OrderRemoteMapper
import com.setyongr.data.remote.models.RequestModel
import com.setyongr.domain.model.Order
import com.setyongr.domain.model.OrderUpdate
import com.setyongr.domain.repository.OrderRepository
import com.setyongr.domain.repository.PartnerOrderRepository
import io.reactivex.Single
import javax.inject.Inject

class OrderDataRepository @Inject constructor(
    private val service: PinjaminService,
    private val orderRemoteMapper: OrderRemoteMapper
) : OrderRepository {
    override fun getOrders(): Single<List<Order>> {
        return service.getOrder().map {
            it.map {
                orderRemoteMapper.mapFromEntity(it)
            }
        }
    }

    override fun getOrderById(id: Int): Single<Order> {
        return service.getOrderById(id).map {
            orderRemoteMapper.mapFromEntity(it)
        }
    }

    override fun placeOrder(id: Int, message: String): Single<Order> {
        return service.placeOrder(RequestModel.Order(id, message)).map {
            orderRemoteMapper.mapFromEntity(it)
        }
    }

}