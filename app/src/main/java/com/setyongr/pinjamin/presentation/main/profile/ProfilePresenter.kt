package com.setyongr.pinjamin.presentation.main.profile

import android.app.Activity
import android.content.Context
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.injection.ActivityContext
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

class ProfilePresenter @Inject constructor(
    private val activity: Activity,
    private val schedulerProvider: SchedulerProvider,
    private val service: PinjaminService,
    private val appState: AppState
): BaseRxPresenter<ProfileView>() {

    fun uploadImage(file: File) {
        getView().showLoading(true)

        val i = file.name.lastIndexOf('.')
        val ext = file.name.substring(i+1)
        val requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                Compressor(activity).compressToFile(file)
        )
        // Create request body for image with random filename without extension
        val imagePart = MultipartBody.Part.createFormData("avatar", UUID.randomUUID().toString() + '.' + ext, requestFile)

        disposables += service.updateMeImage(imagePart)
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            getView().showLoading(false)
                            appState.saveUser(it)
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

    fun pickGallery(preferedResult: Int) {
        RxPaparazzo.single(activity)
                .usingGallery()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribe(
                        {
                            if (it.resultCode() == preferedResult) {
                                uploadImage(it.data().file)
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
                                uploadImage(it.data().file)
                            }
                        },
                        {
                            error -> getView().showError(error)
                        }
                )
    }
}