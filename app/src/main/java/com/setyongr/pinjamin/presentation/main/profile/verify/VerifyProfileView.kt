package com.setyongr.pinjamin.presentation.main.profile.verify

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel

interface VerifyProfileView: BaseLeView {
    fun showUser(user: ResponseModel.User?)
}