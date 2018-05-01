package com.setyongr.pinjamin.presentation.user.main.profile

import com.setyongr.pinjamin.base.BaseLeView

interface ProfileView: BaseLeView {
    fun onSuccess(refreshImage: Boolean = true)
}