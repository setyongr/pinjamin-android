package com.setyongr.pinjamin.presentation.pinjamin.mypinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel

interface MyPinjamanView: BaseLeView {
    fun addPinjaman(pinjaman: ResponseModel.Pinjaman)
}