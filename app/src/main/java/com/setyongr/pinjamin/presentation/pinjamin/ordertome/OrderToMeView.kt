package com.setyongr.pinjamin.presentation.pinjamin.ordertome

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel

interface OrderToMeView: BaseLeView {
    fun addOrder(order: ResponseModel.Order)
}