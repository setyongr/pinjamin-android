package com.setyongr.pinjamin.presentation.partner

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.google.zxing.Result
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.domain.model.OrderStatus
import com.setyongr.domain.interactor.order.partner.UpdateOrderUseCase
import com.setyongr.domain.model.OrderUpdate
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class UsePinjamanActivity: BaseInjectedActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    @Inject
    lateinit var updateOrderUseCase: UpdateOrderUseCase

    var progress: ProgressDialog? = null
    private var id: Int = 0
    private var mode: String = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)

        id = intent.getIntExtra(ID_KEY, 0)
        mode = intent.getStringExtra(MODE_KEY)
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        if (rawResult.text == id.toString()) {
            if (mode == USE_MODE) {
                use()
            } else if (mode == COMPLETE_MODE) {
                complete()
            } else {
                Toast.makeText(this, "Invalid Mode", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Invalid Code!", Toast.LENGTH_LONG).show()
        }
    }

    fun use() {
        val calendar = Calendar.getInstance()
        val datetime = DateTime(calendar)

        val params = OrderUpdate(
                id = id,
                status = OrderStatus.Used(),
                used_at = datetime.toString()
        )

        updateOrderUseCase.execute(params)
                .subscribe(
                        {
                            AlertDialog
                                    .Builder(this).setTitle("Sukses").setMessage("Dipakai!")
                                    .setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
                                        mScannerView!!.resumeCameraPreview(this)
                                        setResult(Activity.RESULT_OK)
                                        finish()
                                    }).show()
                        },
                        {
                            it.printStackTrace()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            mScannerView!!.resumeCameraPreview(this)
                        }
                )
    }

    fun complete() {
        val calendar = Calendar.getInstance()
        val datetime = DateTime(calendar)
        val params = OrderUpdate(
                id = id,
                status = OrderStatus.Completed(),
                finished_at = datetime.toString()
        )

        updateOrderUseCase.execute(params)
                .subscribe(
                        {
                            AlertDialog
                                    .Builder(this).setTitle("Sukses").setMessage("Selesai Dipakai")
                                    .setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
                                        mScannerView!!.resumeCameraPreview(this)
                                        setResult(Activity.RESULT_OK)
                                        finish()
                                    }).show()
                        },
                        {
                            it.printStackTrace()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            mScannerView!!.resumeCameraPreview(this)
                        }
                )
    }

    companion object {
        const val ID_KEY = "id"
        const val MODE_KEY = "mode"
        const val USE_MODE = "use"
        const val COMPLETE_MODE = "complete"

        fun buildIntent(context: Context, id: Int, mode: String): Intent {
            val intent = Intent(context, UsePinjamanActivity::class.java)
            intent.putExtra(ID_KEY, id)
            intent.putExtra(MODE_KEY, mode)
            return intent
        }
    }

}