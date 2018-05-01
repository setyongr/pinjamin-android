package com.setyongr.pinjamin.presentation.partner

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.data.remote.PinjaminService
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import com.setyongr.domain.model.OrderStatus
import com.setyongr.domain.interactor.order.partner.GetPartnerOrderByIdUseCase
import com.setyongr.domain.interactor.order.partner.UpdateOrderUseCase
import com.setyongr.domain.model.Order
import com.setyongr.domain.model.OrderUpdate
import com.setyongr.pinjamin.common.loadUrl
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_order_partner.*


class PartnerOrderDetaillActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var getPartnerOrderByIdUseCase: GetPartnerOrderByIdUseCase

    @Inject
    lateinit var updateOrderUseCase: UpdateOrderUseCase

    var progress: ProgressDialog? = null
    private var id: Int = 0
    var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_partner)
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
        getPartnerOrderByIdUseCase.execute(id)
                .subscribeBy(
                        onSuccess = {
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
        val params = OrderUpdate(id, OrderStatus.Accepted())
        updateOrderUseCase.execute(params)
                .subscribeBy(
                        onSuccess = {
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
        val params = OrderUpdate(id, OrderStatus.Rejected())
        updateOrderUseCase.execute(params)
                .subscribeBy(
                        onSuccess = {
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

    fun show(data: Order) {
        order = data
        penyewa_text.text = data.user.name
        username_text.text = data.user.username
        email_text.text = data.user.email
        hp_text.text = data.user.noHp
        avatar_image.loadUrl(data.user.avatar)
        verified_text.text = if (data.user.verified) "Verified" else "Not Verified"
        message_text.text = data.message

        when(order?.status) {
            is OrderStatus.Accepted, is OrderStatus.Rejected, is OrderStatus.Used, is OrderStatus.Completed -> {
                accept_button.visibility = View.GONE
                reject_button.visibility = View.GONE
            }
        }
        when (order?.status) {
            is OrderStatus.Accepted -> {
                used_complete_button.visibility = View.VISIBLE
                used_complete_button.text = "Mulai Pakai"
                used_complete_button.setOnClickListener {
                    startActivityForResult(UsePinjamanActivity.buildIntent(this, id, UsePinjamanActivity.USE_MODE), 1001)
                }
            }

            is OrderStatus.Used -> {
                used_complete_button.visibility = View.VISIBLE
                used_complete_button.text = "Selesai Pakai"
                used_complete_button.setOnClickListener {
                    startActivityForResult(UsePinjamanActivity.buildIntent(this, id, UsePinjamanActivity.COMPLETE_MODE), 1001)
                }
            }

        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            load()
        }
    }

}