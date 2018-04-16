package com.setyongr.pinjamin.data.models

import com.google.gson.annotations.JsonAdapter
import com.setyongr.pinjamin.data.adapter.OrderStatusAdapter

class RequestModel {
    data class Login(
            val username: String,
            val password: String
    )

    data class Register(
            val name: String,
            val username: String,
            val email: String,
            val password: String
    )

    data class User(
            val name: String? = null,
            val email: String? = null,
            val noHp: String? = null
    )

    data class Order(
            val pinjam_id: Int,
            val message: String
    )

    data class OrderToMe(
            @JsonAdapter(OrderStatusAdapter::class)
            val status: OrderStatus
    )
}