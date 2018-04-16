package com.setyongr.pinjamin.presentation.detail

import android.Manifest
import android.animation.ArgbEvaluator
import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.presentation.order.OrderActivity


class DetailActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

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
            val intent = Intent(this, OrderActivity::class.java)
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
    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

}