package com.setyongr.pinjamin.presentation.mydetail

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.Pinjaman
import java.io.File

interface MyDetailView: BaseLeView {
    fun showImage(file: File)
    fun onSuccess()
    fun show(pinjaman: Pinjaman)
}