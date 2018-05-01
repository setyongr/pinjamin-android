package com.setyongr.pinjamin.presentation.partner.pinjamandetail

import android.app.Activity
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.pinjaman.GetPinjamanByIdUseCase
import com.setyongr.domain.interactor.pinjaman.partner.DeletePinjamanUseCase
import com.setyongr.domain.interactor.pinjaman.partner.UpdatePinjamanUseCase
import id.zelory.compressor.Compressor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class ParnetPinjamanDetailPresenter @Inject constructor(
        private val updatePinjamanUseCase: UpdatePinjamanUseCase,
        private val deletePinjamanUseCase: DeletePinjamanUseCase,
        private val getPinjamanByIdUseCase: GetPinjamanByIdUseCase,
        private val activity: PartnerPinjamanDetailActivity, // TODO: Handle this please
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<PartnerPinjamanDetailView>() {
    var imageFile: File? = null

    fun save(id: Int, name: String, deskripsi: String) {
        getView().showLoading(true)

        val params = UpdatePinjamanUseCase.PinjamanParams(
                id = id,
                name = name,
                description = deskripsi,
                image = imageFile
        )

        disposables += updatePinjamanUseCase.execute(params)
                .subscribeBy(
                        onSuccess = {
                            imageFile = null
                            getView().showLoading(false)
                            getView().onSuccess()
                        },
                        onError = {
                            it.printStackTrace()
                            if (it is HttpException) {
                                println(it.response().errorBody()?.string())
                            }
                            getView().showLoading(false)
                            getView().showError(it)
                        }
                )
    }

    fun delete(id: Int) {
        getView().showLoading(true)
        disposables += deletePinjamanUseCase.execute(id)
                .subscribeBy(
                        onComplete = {
                            getView().showLoading(false)
                            getView().onSuccess()
                        },
                        onError = {
                            it.printStackTrace()
                            if (it is HttpException) {
                                println(it.response().errorBody()?.string())
                            }
                            getView().showLoading(false)
                            getView().showError(it)
                        }
                )
    }

    fun load(id: Int) {
        getView().showLoading(true)
        disposables += getPinjamanByIdUseCase.execute(id)
                .subscribeBy(
                        onSuccess = {
                            getView().showLoading(false)
                            getView().show(it)
                        },
                        onError = {
                            it.printStackTrace()
                            if (it is HttpException) {
                                println(it.response().errorBody()?.string())
                            }
                            getView().showLoading(false)
                            getView().showError(it)
                        }
                )
    }

    fun showImage(file: File) {
        getView().showImage(file)
        imageFile = Compressor(activity).compressToFile(file)
    }

    fun pickGallery(preferedResult: Int) {
        RxPaparazzo.single(activity)
                .usingGallery()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribe(
                        {
                            if (it.resultCode() == preferedResult) {
                                showImage(it.data().file)
                            }
                        },
                        {
                            error -> getView().showError(error)
                        }
                )
    }

    fun pickCamera(preferedResult: Int) {
        RxPaparazzo.single(activity)
                .usingCamera()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribe(
                        {
                            if (it.resultCode() == preferedResult) {
                                showImage(it.data().file)
                            }
                        },
                        {
                            error -> getView().showError(error)
                        }
                )
    }
}