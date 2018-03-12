package com.setyongr.pinjamin.presentation.pinjamin.addpinjaman

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import kotlinx.android.synthetic.main.fragment_pinjamin.*
import java.io.File
import javax.inject.Inject

class AddPinjamanFragment : BaseInjectedFragment(), AddPinjamanView {
    @Inject
    lateinit var mPresenter: AddPinjamanPresenter

    var progress: ProgressDialog? = null

    override fun getLayout(): Int = R.layout.fragment_pinjamin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this)

        pinjam_image.setOnClickListener {
            val dialog = AlertDialog.Builder(context!!)
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
            mPresenter.save(judu_input.editText?.text.toString(), deskripsi_input.editText?.text.toString())
        }
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showLoading(status: Boolean) {
        progress?.dismiss()
        if (status) {
            progress = ProgressDialog.show(context, "Loading", "Please wait...", true, false)
        }
    }

    override fun showError(e: Throwable) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

    override fun showImage(file: File) {
        pinjam_image.setImageBitmap(BitmapFactory.decodeFile(file.path))
    }

    override fun onSuccess(pinjaman: ResponseModel.Pinjaman) {
        // Reset
        judu_input.editText?.setText("")
        deskripsi_input.editText?.setText("")
        pinjam_image.setImageResource(R.drawable.ic_image_white_24dp)

        val dialog = AlertDialog.Builder(context!!)
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