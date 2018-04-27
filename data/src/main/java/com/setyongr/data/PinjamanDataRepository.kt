package com.setyongr.data

import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.PinjamanRemoteMapper
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PinjamanRepository
import io.reactivex.Single
import javax.inject.Inject

class PinjamanDataRepository @Inject constructor(
        private val pinjaminService: PinjaminService,
        private val pinjamanRemoteMapper: PinjamanRemoteMapper
): PinjamanRepository {
    override fun getPinjaman(): Single<List<Pinjaman>> {
        return pinjaminService.getPinjaman().map {
            it.map {
                pinjamanRemoteMapper.mapFromEntity(it)
            }
        }
    }

    override fun getPinjamanById(id: Int): Single<Pinjaman> {
        return pinjaminService.getPinjamanById(id).map {
            pinjamanRemoteMapper.mapFromEntity(it)
        }
    }

}