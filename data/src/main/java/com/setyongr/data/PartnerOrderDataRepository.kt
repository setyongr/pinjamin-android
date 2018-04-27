package com.setyongr.data

import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.OrderRemoteMapper
import com.setyongr.data.remote.models.RequestModel
import com.setyongr.domain.model.Order
import com.setyongr.domain.model.OrderUpdate
import com.setyongr.domain.repository.PartnerOrderRepository
import io.reactivex.Single
import javax.inject.Inject

class PartnerOrderDataRepository @Inject constructor(
    private val service: PinjaminService,
    private val orderRemoteMapper: OrderRemoteMapper
) : PartnerOrderRepository {
    override fun getOrders(): Single<List<Order>> {
        return service.getOrderToMe().map {
            it.map {
                orderRemoteMapper.mapFromEntity(it)
            }
        }
    }

    override fun getOrderById(id: Int): Single<Order> {
        return service.getOrderToMeById(id).map {
            orderRemoteMapper.mapFromEntity(it)
        }
    }

    override fun updateOrder(id: Int, orderUpdate: OrderUpdate): Single<Order> {
        val order = RequestModel.OrderToMe(orderUpdate.status, orderUpdate.used_at, orderUpdate.finished_at)
        return service.updateOrderToMe(id, order).map {
            orderRemoteMapper.mapFromEntity(it)
        }
    }

}