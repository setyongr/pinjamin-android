package com.setyongr.pinjamin.presentation.pinjamin.addpinjaman

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.data.remote.models.ResponseModel
import com.setyongr.domain.model.Pinjaman
import java.io.File

interface AddPinjamanView : BaseLeView {
    fun showImage(file: File)
    fun onSuccess(pinjaman: Pinjaman)
}