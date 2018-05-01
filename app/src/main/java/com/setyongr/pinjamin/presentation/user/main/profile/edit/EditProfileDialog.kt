package com.setyongr.pinjamin.presentation.user.main.profile.edit

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedDialog
import com.setyongr.pinjamin.injection.component.ActivityComponent
import kotlinx.android.synthetic.main.dialog_edit_profile.*
import javax.inject.Inject

class EditProfileDialog : BaseInjectedDialog(), EditProfileView {
    companion object {
        const val TYPE_KEY = "type"
        const val EMAIL_TYPE = 1
        const val PHONE_TYPE = 2
        const val NAME_TYPE = 3

        fun createInstance(type: Int): EditProfileDialog {
            val bundle = Bundle()
            bundle.putInt(TYPE_KEY, type)
            val m = EditProfileDialog()
            m.arguments = bundle
            return m
        }
    }

    @Inject
    lateinit var mPresenter: EditProfilePresenter

    var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this)
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

    override fun onSuccess() {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
        dismiss()
    }

    override fun getLayout(): Int = R.layout.dialog_edit_profile

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.setCanceledOnTouchOutside(false)

        val type = arguments?.getInt(TYPE_KEY, EMAIL_TYPE)

        close_button.setOnClickListener {
            dismiss()
        }

        when(type) {
            EMAIL_TYPE -> {
                placeholder_text.text = "Masukkan Email Baru"
                edit_input.hint = "Email"
                save_button.setOnClickListener {
                    mPresenter.saveEmail(edit_input.editText?.text.toString())
                }
            }
            PHONE_TYPE -> {
                placeholder_text.text = "Masukkan No HP Baru"
                edit_input.hint = "No HP"
                save_button.setOnClickListener {
                    mPresenter.saveHP(edit_input.editText?.text.toString())
                }
            }
            NAME_TYPE -> {
                placeholder_text.text = "Masukkan Nama Baru"
                edit_input.hint = "Nama"
                save_button.setOnClickListener {
                    mPresenter.saveName(edit_input.editText?.text.toString())
                }
            }
        }
    }
}