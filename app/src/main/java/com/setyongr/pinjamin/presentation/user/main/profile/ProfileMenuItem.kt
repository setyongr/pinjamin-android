package com.setyongr.pinjamin.presentation.user.main.profile

data class ProfileMenuItem(val title: String, val value: String?, val onClick: (() -> Unit)? = null)