package com.setyongr.pinjamin.presentation.partner.pinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Pinjaman

interface PartnerPinjamanView: BaseLeView {
    fun addPinjaman(pinjaman: Pinjaman)
}