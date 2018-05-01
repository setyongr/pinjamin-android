package com.setyongr.pinjamin.presentation.user.pinjamandetail

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import javax.inject.Inject
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import com.setyongr.domain.interactor.pinjaman.GetPinjamanByIdUseCase
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.presentation.user.placeorder.PlaceOrderActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_detail.*


class PinjamanDetailActivity: BaseInjectedActivity() {
    @Inject
    lateinit var getPinjamanByIdUseCaseUseCase: GetPinjamanByIdUseCase

    @Inject
    lateinit var appState: AppState

    var progress: ProgressDialog? = null
    private var id: Int = 0

    val HIDE_ORDER = "hide_order"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        appBar.addOnOffsetChangedListener({ _, verticalOffset ->
//            val eval = ArgbEvaluator().evaluate(verticalOffset.toFloat() / collapsingToolbar.height, ContextCompat.getColor(this, R.color.colorTextGrey), ContextCompat.getColor(this, R.color.colorWhite))
//            toolbar.navigationIcon?.setColorFilter(eval as Int, PorterDuff.Mode.SRC_ATOP)
            if (collapsingToolbar.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.colorTextGrey), PorterDuff.Mode.SRC_ATOP)
            } else {
                toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)
            }
        })

        id = intent.extras.getInt("id")
        load(id)

        call_now.setOnClickListener {
            call()
        }

        email_now.setOnClickListener {
            sendEmail()
        }

        order_button.setOnClickListener {
            val intent = Intent(this, PlaceOrderActivity::class.java)
            intent.putExtra("id", id)
            move(intent, finishActivity = true)
        }

        if (intent?.hasExtra(HIDE_ORDER) == true) {
            order_card.visibility = View.INVISIBLE
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
        getPinjamanByIdUseCaseUseCase.execute(id)
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
        collapsingToolbar.title = data.name
        overview.text = data.deskripsi
        backdrop.loadUrl(data.image)
        poster.loadUrl(data.image)
        penyewa.text = data.user.name
        username_text.text = data.user.username
        email.text = data.user.email
        hp.text = data.user.noHp
        verified.text = if (data.user.verified == true) "Terverifikasi" else "Belum"
        avatar_image.loadUrl(data.user.avatar)

        if (data.user.id == appState.getUser()?.id) {
            order_card.visibility = View.INVISIBLE
        }
    }

    fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email.text.toString(), null))

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email.text.toString()))

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show()
        }

    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + hp.text)
        startActivity(intent)
    }

}