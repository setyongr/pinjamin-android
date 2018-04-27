package com.setyongr.pinjamin.presentation.main.order

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Order

interface OrderView: BaseLeView {
    fun addOrder(order: Order)
}