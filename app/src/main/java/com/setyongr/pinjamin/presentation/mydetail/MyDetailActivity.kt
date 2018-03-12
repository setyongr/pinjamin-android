package com.setyongr.pinjamin.presentation.mydetail

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_detail_my.*


class MyDetailActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    var progress: ProgressDialog? = null
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_my)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        id = intent.extras.getInt("id")

        load()

        delete.setOnClickListener {
            delete()
        }

    }

    fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(this, "Loading", "Please wait...", true, false)
        }
    }

    fun load() {
        showLoading(true)
        service.getPinjamanById(id)
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            showLoading(false)
                            show(it)
                        },
                        onError = {
                            it.printStackTrace()
                            showLoading(false)
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                )
    }

    fun delete() {
        showLoading(true)
        service.deleteMyPinjaman(id)
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onComplete = {
                            showLoading(false)
                            val dialog = AlertDialog.Builder(this)
                                    .setTitle("Success")
                                    .setMessage("Deleted")
                                    .setPositiveButton("OK") {
                                        dialogInterface: DialogInterface, _: Int ->
                                        dialogInterface.dismiss()

                                        finish()
                                    }
                                    .create()

                            dialog.show()
                        },
                        onError = {
                            it.printStackTrace()
                            showLoading(false)
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                )
    }

    fun show(data: ResponseModel.Pinjaman) {
        collapsingToolbar.title = data.name
        overview.text = data.deskripsi
        backdrop.loadUrl(data.image)
        poster.loadUrl(data.image)
    }


    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

}