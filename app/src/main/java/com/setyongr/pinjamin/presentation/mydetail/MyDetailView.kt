package com.setyongr.pinjamin.presentation.mydetail

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel
import java.io.File

interface MyDetailView: BaseLeView {
    fun showImage(file: File)
    fun onSuccess()
    fun show(pinjaman: ResponseModel.Pinjaman)
}