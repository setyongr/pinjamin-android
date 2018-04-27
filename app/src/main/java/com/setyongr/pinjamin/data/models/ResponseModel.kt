package com.setyongr.pinjamin.data.models

import com.google.gson.annotations.JsonAdapter
import com.setyongr.pinjamin.data.adapter.OrderStatusAdapter

class ResponseModel {
    data class Token (
            val token: String
    )

    data class Register (
            val id: Int,
            val email: String,
            val username: String
    )

    data class User (
            val id: Int,
            val username: String,
            val name: String,
            val email: String,
            val noHp: String?,
            val ktpImage: String?,
            val ktmImage: String?,
            val avatar: String?,
            val verified: Boolean,
            val point: Int
    )

    data class Pinjaman(
            val id: Int,
            val deskripsi: String,
            val name: String,
            val image: String?,
            val user: User
    )

    data class Order(
            val id: Int,
            val user: User,
            val pinjam: Pinjaman,
            val message: String,
            @JsonAdapter(OrderStatusAdapter::class)
            val status: OrderStatus,
            val crated_at: String?,
            val updated_at: String?,
            val used_at: String?,
            val finished_at: String?
    )

    data class SignUpError(
            val name: List<String>? = null,
            val email: List<String>? = null,
            val username: List<String>? = null,
            val password: List<String>? = null
    )
}