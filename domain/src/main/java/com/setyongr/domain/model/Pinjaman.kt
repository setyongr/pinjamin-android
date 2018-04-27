package com.setyongr.domain.model

data class Pinjaman(
        val id: Int,
        val deskripsi: String,
        val name: String,
        val image: String?,
        val user: User
)