package com.setyongr.pinjamin.presentation.mydetail

import android.app.Activity
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import id.zelory.compressor.Compressor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.util.*
import javax.inject.Inject

class MyDetailPresenter @Inject constructor(
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider,
        private val activity: Activity
): BaseRxPresenter<MyDetailView>() {
    var imagePart: MultipartBody.Part? = null

    fun save(id: Int, name: String, deskripsi: String) {
        getView().showLoading(true)

        disposables += service.updatePinjaman(id, name, deskripsi, imagePart)
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            imagePart = null
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
        disposables += service.deleteMyPinjaman(id)
                .applyDefaultSchedulers(schedulerProvider)
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
        disposables += service.getPinjamanById(id)
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
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


        val i = file.name.lastIndexOf('.')
        val ext = file.name.substring(i+1)
        val requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                Compressor(activity).compressToFile(file)
        )
        // Create request body for image with random filename without extension
        imagePart = MultipartBody.Part.createFormData("image", UUID.randomUUID().toString() + '.' + ext, requestFile)

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