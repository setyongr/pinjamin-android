package com.setyongr.pinjamin.presentation.mydetail

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.injection.component.ActivityComponent
import javax.inject.Inject
import android.graphics.BitmapFactory
import android.support.v7.app.AlertDialog
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail_my.*
import java.io.File


class MyDetailActivity: BaseInjectedActivity(), MyDetailView {
    @Inject
    lateinit var mPresenter: MyDetailPresenter

    var progress: ProgressDialog? = null
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_my)
        mPresenter.attachView(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        id = intent.extras.getInt("id")
        mPresenter.load(id)

        save_button.setOnClickListener {
            mPresenter.save(id, title_input.editText?.text.toString(), description_input.editText?.text.toString())
        }

        delete_button.setOnClickListener {
            mPresenter.delete(id)
        }

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
    }

    override fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(this, "Loading", "Please wait...", true, false)
        }
    }
    override fun show(pinjaman: Pinjaman) {
        title_input.editText?.setText(pinjaman.name)
        description_input.editText?.setText(pinjaman.deskripsi)
        pinjam_image.loadUrl(pinjaman.image)
    }


    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showError(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

    override fun showImage(file: File) {
        pinjam_image.setImageBitmap(BitmapFactory.decodeFile(file.path))
    }

    override fun onSuccess() {
        val dialog = AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Operation Success")
                .setPositiveButton("OK") {
                    dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()

                    finish()
                }
                .create()

        dialog.show()
    }
}