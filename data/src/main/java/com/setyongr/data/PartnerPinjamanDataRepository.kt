package com.setyongr.data

import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.PinjamanRemoteMapper
import com.setyongr.domain.model.Pinjaman
import com.setyongr.domain.repository.PartnerPinjamanRepository
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

class PartnerPinjamanDataRepository @Inject constructor(
        private val pinjaminService: PinjaminService,
        private val pinjamanRemoteMapper: PinjamanRemoteMapper
): PartnerPinjamanRepository {
    override fun getPartnerPinjaman(): Single<List<Pinjaman>> {
        return pinjaminService.getMyPinjaman().map {
            it.map {
                pinjamanRemoteMapper.mapFromEntity(it)
            }
        }
    }

    override fun deletePartnerPinjaman(id: Int): Completable {
        return pinjaminService.deleteMyPinjaman(id)
    }

    override fun createPinjaman(name: String, description: String, image: File?): Single<Pinjaman> {
        val imagePart = image?.let {
            val i = image.name.lastIndexOf('.')
            val ext = image.name.substring(i+1)
            val requestFile = RequestBody.create(
                    MediaType.parse("image/*"),
                    image
            )
            MultipartBody.Part.createFormData("image", UUID.randomUUID().toString() + '.' + ext, requestFile)
        }

        return pinjaminService.savePinjaman(name, description, imagePart)
                .map {
                    pinjamanRemoteMapper.mapFromEntity(it)
                }
    }

    override fun updatePinjaman(id: Int, name: String?, description: String?, image: File?): Single<Pinjaman> {
        val imagePart = image?.let {
            val i = image.name.lastIndexOf('.')
            val ext = image.name.substring(i+1)
            val requestFile = RequestBody.create(
                    MediaType.parse("image/*"),
                    image
            )
            MultipartBody.Part.createFormData("image", UUID.randomUUID().toString() + '.' + ext, requestFile)
        }

        return pinjaminService.updatePinjaman(id, name, description, imagePart)
                .map {
                    pinjamanRemoteMapper.mapFromEntity(it)
                }
    }

}