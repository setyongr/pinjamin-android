package com.setyongr.pinjamin.presentation.partner.createpinjaman

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.injection.component.ActivityComponent
import kotlinx.android.synthetic.main.activity_create_pinjaman.*
import java.io.File
import javax.inject.Inject

class CreatePinjamanActivity : BaseInjectedActivity(), CreatePinjamanView {
    @Inject
    lateinit var mPresenter: CreatePinjamanPresenter

    var progress: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pinjaman)

        mPresenter.attachView(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        pinjam_image.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Update Gambar")
                    .setItems(arrayOf("Camera", "Gallery", "Cancel"), {
                        dialogInterface: DialogInterface, i: Int ->
                        when(i){
                            0 -> {
                                mPresenter.pickCamera(Activity.RESULT_OK)
                            }
                            1 -> {
                                mPresenter.pickGallery(Activity.RESULT_OK)
                            }
                            2 -> {
                                dialogInterface.dismiss()
                            }
                        }
                    })
            dialog.show()
        }

        save_button.setOnClickListener {
            mPresenter.save(title_input.editText?.text.toString(), description_input.editText?.text.toString())
        }

    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
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

    override fun showImage(file: File) {
        pinjam_image.setImageBitmap(BitmapFactory.decodeFile(file.path))
    }

    override fun onSuccess(pinjaman: Pinjaman) {
        // Reset
        title_input.editText?.setText("")
        description_input.editText?.setText("")
        pinjam_image.setImageResource(R.drawable.ic_image_white_24dp)

        val dialog = AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Kirim Sukses")
                .setPositiveButton("OK") {
                    dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()

                }
                .create()

        dialog.show()
    }

}