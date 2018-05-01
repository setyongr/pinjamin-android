package com.setyongr.pinjamin.presentation.partner.createpinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Pinjaman
import java.io.File

interface CreatePinjamanView : BaseLeView {
    fun showImage(file: File)
    fun onSuccess(pinjaman: Pinjaman)
}