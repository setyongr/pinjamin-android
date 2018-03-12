package com.setyongr.pinjamin.presentation.main.profile

data class ProfileMenuItem(val title: String, val value: String?, val onClick: (() -> Unit)? = null)