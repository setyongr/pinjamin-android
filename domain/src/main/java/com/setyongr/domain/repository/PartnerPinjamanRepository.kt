package com.setyongr.domain.repository

import com.setyongr.domain.model.Pinjaman
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

/**
 * Partner Specific pinjaman repository
 */

interface PartnerPinjamanRepository {
    fun getPartnerPinjaman() : Single<List<Pinjaman>>
    fun deletePartnerPinjaman(id: Int) : Completable
    fun createPinjaman(name: String, description: String, image: File?) : Single<Pinjaman>
    fun updatePinjaman(id: Int, name: String?, description: String?, image: File?) : Single<Pinjaman>
}