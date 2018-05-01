package com.setyongr.pinjamin.presentation.partner.order

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Order

interface PartnerOrderView: BaseLeView {
    fun addOrder(order: Order)
}