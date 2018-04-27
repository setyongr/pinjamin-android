package com.setyongr.pinjamin.presentation.orderdetail

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.OrderStatus
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_qrview.*
import javax.inject.Inject

class QrViewerActivity: BaseInjectedActivity() {
    @Inject
    lateinit var service: PinjaminService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var appState: AppState

    var progress: ProgressDialog? = null
    private var id: Int = 0

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrview)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        id= intent.getIntExtra("id", 0)

        try {
            val bitmap = encodeAsBitmap(id.toString())
            qr_image.setImageBitmap(bitmap)
        }catch (e: WriterException) {
            e.printStackTrace()
        }

        call_now.setOnClickListener {
            call()
        }

        email_now.setOnClickListener {
            sendEmail()
        }

        load(id)

    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String): Bitmap? {
        val WIDTH = 400
        val HEIGHT = 400
        val WHITE = 0xFFFFFFFF
        val BLACK = 0xFF000000
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null)
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }

        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result.get(x, y)) BLACK.toInt() else WHITE.toInt()
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }

    fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(this, "Loading", "Please wait...", true, false)
        }
    }

    fun load(id: Int) {
        showLoading(true)
        service.getOrderById(id)
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

    fun show(data: ResponseModel.Order) {

        penyewa_text.text = data.pinjam.user.name
        username_text.text = data.pinjam.user.username
        email_text.text = data.pinjam.user.email
        hp_text.text = data.pinjam.user.noHp
        verified_text.text = if (data.user.verified == true) "Terverifikasi" else "Belum"
        description_text.text = data.pinjam.deskripsi
        avatar_image.loadUrl(data.pinjam.user.avatar)

        if (data.status !is OrderStatus.Waiting) {
            cancel_button.visibility = View.INVISIBLE
        }

        message_text.text = data.message
    }

    fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email_text.text.toString(), null))

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email_text.text.toString()))

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show()
        }

    }

    fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + hp_text.text)
        startActivity(intent)
    }

    companion object {
        fun open(context: Context, id: Int) {
            val intent = Intent(context, QrViewerActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }
}