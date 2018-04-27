package com.setyongr.pinjamin.presentation.main.profile

import android.app.Activity
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.domain.interactor.user.CurrentUserUseCase
import com.setyongr.domain.interactor.user.UpdateUserImageUseCase
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import id.zelory.compressor.Compressor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
        private val activity: Activity,
        private val currentUserUseCase: CurrentUserUseCase,
        private val updateUserImageUseCase: UpdateUserImageUseCase,
        private val schedulerProvider: SchedulerProvider,
        private val appState: AppState
): BaseRxPresenter<ProfileView>() {

    fun uploadImage(file: File) {
        getView().showLoading(true)

        val imageParam = UpdateUserImageUseCase.ImageParam("avatar",
                Compressor(activity).compressToFile(file))

        disposables += updateUserImageUseCase.execute(imageParam)
                .subscribeBy(
                        onSuccess = {
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

    fun refreshUser() {
        getView().showLoading(true)
        disposables += currentUserUseCase.execute()
                .subscribeBy(
                        onSuccess = {
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

    fun refreshUserSilent() {
        disposables += currentUserUseCase.execute()
                .subscribeBy(
                        onSuccess = {
                            appState.saveUser(it)
                            getView().onSuccess(false)
                        },
                        onError = {
                            it.printStackTrace()
                        }
                )
    }
}