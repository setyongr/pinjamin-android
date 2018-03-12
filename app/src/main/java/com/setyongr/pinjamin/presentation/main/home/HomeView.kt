package com.setyongr.pinjamin.presentation.main.home

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel

interface HomeView: BaseLeView {
    fun addPinjaman(pinjaman: ResponseModel.Pinjaman)
}