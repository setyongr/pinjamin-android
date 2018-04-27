package com.setyongr.pinjamin.presentation.pinjamin.mypinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Pinjaman

interface MyPinjamanView: BaseLeView {
    fun addPinjaman(pinjaman: Pinjaman)
}