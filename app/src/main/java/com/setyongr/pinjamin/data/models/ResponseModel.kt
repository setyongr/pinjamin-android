package com.setyongr.pinjamin.data.models

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
            val verified: Boolean
    )

    data class Pinjaman(
            val id: Int,
            val deskripsi: String,
            val name: String,
            val image: String,
            val user: User
    )

    data class Order(
            val id: Int,
            val user: User,
            val pinjam: Pinjaman,
            val message: String,
            val status: Int,
            val created_at: String,
            val updated_at: String,
            val used_at: String?,
            val finished_at: String?
    )
}