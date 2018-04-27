package com.setyongr.pinjamin.presentation.pinjamin.ordertome

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Order

interface OrderToMeView: BaseLeView {
    fun addOrder(order: Order)
}