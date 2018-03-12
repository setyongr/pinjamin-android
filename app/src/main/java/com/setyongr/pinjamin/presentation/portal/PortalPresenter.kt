package com.setyongr.pinjamin.presentation.portal

import com.setyongr.pinjamin.base.BasePresenter
import com.setyongr.pinjamin.data.AppState
import javax.inject.Inject

class PortalPresenter @Inject constructor(
        private val appState: AppState
): BasePresenter<PortalView>() {
    fun checkLoggedIn() {
        if (appState.isLoggedIn()) {
            getView().onLoggedIn()
        }
    }
}