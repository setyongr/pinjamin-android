package com.setyongr.domain.repository

import com.setyongr.domain.model.Pinjaman
import io.reactivex.Single

interface PinjamanRepository {
    fun getPinjaman() : Single<List<Pinjaman>>
    fun getPinjamanById(id: Int) : Single<Pinjaman>
}