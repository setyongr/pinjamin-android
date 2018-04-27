package com.setyongr.domain.model

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

data class RegisterResult (
        val id: Int,
        val email: String,
        val username: String
)

data class Token (
        val token: String
)