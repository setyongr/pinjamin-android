package com.setyongr.pinjamin.presentation.user.main.profile.verify

import android.app.Activity
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.user.UpdateUserImageUseCase
import id.zelory.compressor.Compressor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class VerifyProfilePresenter @Inject constructor(
        private val activity: Activity,
        private val appState: AppState,
        private val schedulerProvider: SchedulerProvider,
        private val updateUserImageUseCase: UpdateUserImageUseCase
): BaseRxPresenter<VerifyProfileView>() {
    companion object {
        val KTP_FLAG = 1
        val KTM_FLAG = 2
    }

    fun getUser() {
        getView().showUser(appState.getUser())
    }

    fun uploadImage(file: File, flag: Int) {
        getView().showLoading(true)

        val imageParam = UpdateUserImageUseCase.ImageParam(if (flag == 1) "ktpImage" else "ktmImage",
                Compressor(activity).compressToFile(file))

        disposables += updateUserImageUseCase.execute(imageParam)
                .subscribeBy(
                        onSuccess = {
                            getView().showLoading(false)
                            appState.saveUser(it)
                            getView().showUser(it)
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

    fun pickGallery(preferedResult: Int, flag: Int) {
        RxPaparazzo.single(activity)
                .usingGallery()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribe(
                        {
                            if (it.resultCode() == preferedResult) {
                                uploadImage(it.data().file, flag)
                            }
                        },
                        {
                            error -> getView().showError(error)
                        }
                )
    }

    fun pickCamera(preferedResult: Int, flag: Int) {
        RxPaparazzo.single(activity)
                .usingCamera()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribe(
                        {
                            if (it.resultCode() == preferedResult) {
                                uploadImage(it.data().file, flag)
                            }
                        },
                        {
                            error -> getView().showError(error)
                        }
                )
    }
}