package com.setyongr.domain.repository

import com.setyongr.domain.model.Order
import com.setyongr.domain.model.OrderUpdate
import io.reactivex.Single

interface PartnerOrderRepository {
    fun getOrders() : Single<List<Order>>
    fun getOrderById(id: Int) : Single<Order>
    fun updateOrder(id: Int, orderUpdate: OrderUpdate) : Single<Order>
}