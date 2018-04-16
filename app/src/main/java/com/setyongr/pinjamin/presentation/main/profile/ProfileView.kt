package com.setyongr.pinjamin.presentation.main.profile

import com.setyongr.pinjamin.base.BaseLeView

interface ProfileView: BaseLeView {
    fun onSuccess(refreshImage: Boolean = true)
}