package com.setyongr.domain.model

data class Order(
        val id: Int,
        val user: User,
        val pinjam: Pinjaman,
        val message: String,
        val status: OrderStatus,
        val crated_at: String?,
        val updated_at: String?,
        val used_at: String?,
        val finished_at: String?
)

data class OrderUpdate(
        val id: Int,
        val status: OrderStatus,
        val used_at: String? = null,
        val finished_at: String? = null
)