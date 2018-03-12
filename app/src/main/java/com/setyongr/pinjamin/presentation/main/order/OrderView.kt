package com.setyongr.pinjamin.presentation.main.order

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel

interface OrderView: BaseLeView {
    fun addOrder(order: ResponseModel.Order)
}