package com.setyongr.pinjamin.presentation.pinjamin

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.data.models.OrderStatus
import com.setyongr.pinjamin.data.models.RequestModel
import kotlinx.android.synthetic.main.activity_order_to_me_detail.*


class OrderToMeDetailActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    var progress: ProgressDialog? = null
    private var id: Int = 0
    var order: ResponseModel.Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_to_me_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""


        id = intent.extras.getInt("id")

        load()

        accept_button.setOnClickListener {
            accept()
        }

        reject_button.setOnClickListener {
            reject()
        }

        call_now.setOnClickListener {
            call()
        }

        email_now.setOnClickListener {
            sendEmail()
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
        service.getOrderToMeById(id)
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

    fun accept() {
        showLoading(true)
        service.updateOrderToMe(id, RequestModel.OrderToMe(OrderStatus.Accepted()))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onComplete = {
                            showLoading(false)
                            val dialog = AlertDialog.Builder(this)
                                    .setTitle("Success")
                                    .setMessage("Accepted")
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

    fun reject() {
        showLoading(true)
        service.updateOrderToMe(id, RequestModel.OrderToMe(OrderStatus.Rejected()))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onComplete = {
                            showLoading(false)
                            val dialog = AlertDialog.Builder(this)
                                    .setTitle("Success")
                                    .setMessage("Rejected")
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

    fun show(data: ResponseModel.Order) {
        order = data
        penyewa_text.text = data.user.name
        username_text.text = data.user.username
        email_text.text = data.user.email
        hp_text.text = data.user.noHp
        avatar_image.loadUrl(data.user.avatar)
        verified_text.text = if (data.user.verified) "Verified" else "Not Verified"
        message_text.text = data.message

        when (order?.status) {
            is OrderStatus.Accepted, is OrderStatus.Rejected-> {
                accept_button.visibility = View.GONE
                reject_button.visibility = View.GONE
            }
        }
    }


    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", order?.user?.email, null))

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(order?.user?.email))

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show()
        }

    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + order?.user?.noHp)
        startActivity(intent)
    }

}