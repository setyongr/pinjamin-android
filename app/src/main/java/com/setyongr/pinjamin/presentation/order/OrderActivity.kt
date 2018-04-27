package com.setyongr.pinjamin.presentation.order

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.data.remote.PinjaminService
import com.setyongr.domain.interactor.order.PlaceOrderUseCase
import com.setyongr.domain.interactor.pinjaman.GetPinjamanByIdUseCase
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.injection.component.ActivityComponent
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_order.*
import javax.inject.Inject


class OrderActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var placeOrderUseCase: PlaceOrderUseCase

    @Inject
    lateinit var getPinjamanByIdUseCase: GetPinjamanByIdUseCase

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
        getPinjamanByIdUseCase.execute(id)
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

    fun show(data: Pinjaman) {
        name_text.text = data.name
        user_text.text = data.user.username
    }

    fun save() {
        showLoading(true)
        val params = PlaceOrderUseCase.OrderParam(id, message_text.editText?.text.toString())
        placeOrderUseCase.execute(params)
                .subscribeBy(
                        onSuccess = {
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
