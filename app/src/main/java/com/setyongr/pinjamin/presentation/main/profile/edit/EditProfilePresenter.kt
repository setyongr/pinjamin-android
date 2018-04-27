package com.setyongr.pinjamin.presentation.main.profile.edit

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.interactor.user.UpdateUserInfoUseCase
import com.setyongr.domain.model.User
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(
        private val updateUserInfoUseCase: UpdateUserInfoUseCase,
        private val appState: AppState
): BaseRxPresenter<EditProfileView>() {
    private val editObserver = object : SingleObserver<User> {

        override fun onSubscribe(d: Disposable) {
            disposables += d
        }

        override fun onSuccess(t: User) {
            getView().showLoading(false)
            appState.saveUser(t)
            getView().onSuccess()
        }

        override fun onError(e: Throwable) {
            getView().showLoading(false)
            getView().showError(e)
        }

    }
    fun saveHP(hp: String) {
        getView().showLoading(true)
        val params = UpdateUserInfoUseCase.UserInfoParam(phone = hp)
        updateUserInfoUseCase.execute(params)
                .subscribeWith(editObserver)
    }

    fun saveEmail(email: String) {
        getView().showLoading(true)
        val params = UpdateUserInfoUseCase.UserInfoParam(email = email)
        updateUserInfoUseCase.execute(params)
                .subscribeWith(editObserver)
    }

    fun saveName(name: String) {
        getView().showLoading(true)
        val params = UpdateUserInfoUseCase.UserInfoParam(name = name)
        updateUserInfoUseCase.execute(params)
                .subscribeWith(editObserver)
    }
}