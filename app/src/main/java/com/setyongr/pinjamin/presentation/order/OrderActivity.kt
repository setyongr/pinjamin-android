package com.setyongr.pinjamin.presentation.order

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.RequestModel
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_order.*
import javax.inject.Inject
import android.graphics.PorterDuff
import com.setyongr.pinjamin.R.id.collapsingToolbar
import android.support.v4.view.ViewCompat
import android.opengl.ETC1.getHeight
import android.support.design.widget.AppBarLayout



class OrderActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }


    var progress: ProgressDialog? = null
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""

        id = intent.extras.getInt("id")
        load(id)

        order_button.setOnClickListener {
            save()
        }

    }

    fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(this, "Loading", "Please wait...", true, false)
        }
    }

    fun load(id: Int) {
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

    fun show(data: ResponseModel.Pinjaman) {
        name_text.text = data.name
        user_text.text = data.user.username
    }

    fun save() {
        showLoading(true)
        service.placeOrder(RequestModel.Order(id, message_text.editText?.text.toString()))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            showLoading(false)
                            val dialog = AlertDialog.Builder(this)
                                    .setTitle("Success")
                                    .setMessage("Order Dibuat")
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
}
