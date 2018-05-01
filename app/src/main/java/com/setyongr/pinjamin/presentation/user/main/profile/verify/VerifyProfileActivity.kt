package com.setyongr.pinjamin.presentation.user.main.profile.verify

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.domain.model.User
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.injection.component.ActivityComponent
import kotlinx.android.synthetic.main.activity_verify_profile.*
import javax.inject.Inject

class VerifyProfileActivity: BaseInjectedActivity(), VerifyProfileView {
    @Inject
    lateinit var mPresenter: VerifyProfilePresenter

    var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
        setContentView(R.layout.activity_verify_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Verifikasi Profil"

        edit_ktp_button.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            .setTitle("Update KTP")
            .setItems(arrayOf("Camera", "Gallery", "Cancel"), {
                dialogInterface: DialogInterface, i: Int ->
                when(i){
                    0 -> {
                        mPresenter.pickCamera(Activity.RESULT_OK, VerifyProfilePresenter.KTP_FLAG)
                    }
                    1 -> {
                        mPresenter.pickGallery(Activity.RESULT_OK, VerifyProfilePresenter.KTP_FLAG)
                    }
                    2 -> {
                        dialogInterface.dismiss()
                    }
                }
            })
            dialog.show()
        }

        edit_ktm_button.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Update KTM")
                    .setItems(arrayOf("Camera", "Gallery", "Cancel"), {
                        dialogInterface: DialogInterface, i: Int ->
                        when(i){
                            0 -> {
                                mPresenter.pickCamera(Activity.RESULT_OK, VerifyProfilePresenter.KTM_FLAG)
                            }
                            1 -> {
                                mPresenter.pickGallery(Activity.RESULT_OK, VerifyProfilePresenter.KTM_FLAG)
                            }
                            2 -> {
                                dialogInterface.dismiss()
                            }
                        }
                    })
            dialog.show()
        }

        request_button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        mPresenter.getUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showUser(user: User?) {
        user?.let {
            ktm_image.loadUrl(it.ktmImage)
            ktp_image.loadUrl(it.ktpImage)
            verified_text.text = if (it.verified) "Terverifikasi" else "Belum Terverifikasi"
        }
    }

    override fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(this, "Loading", "Please wait...", true, false)
        }
    }

    override fun showError(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

}