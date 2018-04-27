package com.setyongr.pinjamin.presentation.pinjamin.addpinjaman

import android.app.Activity
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.pinjaman.partner.CreatePinjamanUseCase
import id.zelory.compressor.Compressor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class AddPinjamanPresenter @Inject constructor(
        private val createPinjamanUseCase: CreatePinjamanUseCase,
        private val activity: Activity,
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<AddPinjamanView>() {
    var imageFile: File? = null

    fun save(name: String, deskripsi: String) {
        getView().showLoading(true)

        val params = CreatePinjamanUseCase.PinjamanParams(name, deskripsi, imageFile)
        disposables += createPinjamanUseCase.execute(params)
                .subscribeBy(
                        onSuccess = {
                            imageFile = null
                            getView().showLoading(false)
                            getView().onSuccess(it)
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