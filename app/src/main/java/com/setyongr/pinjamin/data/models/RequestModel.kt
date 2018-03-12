package com.setyongr.pinjamin.data.models

class RequestModel {
    data class Login(
            val username: String,
            val password: String
    )

    data class Register(
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
            val status: Int
    )
}