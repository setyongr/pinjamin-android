package com.setyongr.pinjamin.presentation.partner.pinjamandetail

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Pinjaman
import java.io.File

interface PartnerPinjamanDetailView: BaseLeView {
    fun showImage(file: File)
    fun onSuccess()
    fun show(pinjaman: Pinjaman)
}