package com.setyongr.domain.repository

import com.setyongr.domain.model.Order
import io.reactivex.Single

interface OrderRepository {
    fun placeOrder(id: Int, message: String) : Single<Order>
    fun getOrders() : Single<List<Order>>
    fun getOrderById(id: Int) : Single<Order>
}