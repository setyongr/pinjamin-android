package com.setyongr.pinjamin.presentation.user.main.home

import com.setyongr.pinjamin.base.BaseLeView
import com.setyongr.domain.model.Pinjaman

interface HomeView: BaseLeView {
    fun addPinjaman(pinjaman: Pinjaman)
}