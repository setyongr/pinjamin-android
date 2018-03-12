package com.setyongr.pinjamin.presentation.pinjamin.addpinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.pinjamin.data.models.ResponseModel
import java.io.File

interface AddPinjamanView : BaseLeView {
    fun showImage(file: File)
    fun onSuccess(pinjaman: ResponseModel.Pinjaman)
}