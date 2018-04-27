package com.setyongr.pinjamin.presentation.main.profile.verify

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.User

interface VerifyProfileView: BaseLeView {
    fun showUser(user: User?)
}