package com.setyongr.pinjamin.presentation.main.profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.pinjamin.common.getBaseActivity
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.main.profile.edit.EditProfileDialog
import com.setyongr.pinjamin.presentation.main.profile.verify.VerifyProfileActivity
import com.setyongr.pinjamin.presentation.pinjamin.PinjaminActivity
import com.setyongr.pinjamin.presentation.portal.PortalActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment: BaseInjectedFragment(), ProfileView {
    @Inject
    lateinit var mPresenter: ProfilePresenter

    @Inject
    lateinit var appState: AppState

    var progress: ProgressDialog? = null
    val adapter by lazy {
        ProfileAdapter()
    }

    override fun getLayout(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this)

        getBaseActivity().setSupportActionBar(toolbar)

        list.layoutManager = LinearLayoutManager(context)
        list.addItemDecoration(DividerItemDecoration(context, (list.layoutManager as LinearLayoutManager).orientation))
        list.adapter = adapter

        loadUser()

        profile_image.setOnClickListener {
            val dialog = AlertDialog.Builder(context!!)
                    .setTitle("Update Avatar")
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

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }
    private fun loadUser() {
        adapter.clear()
        val user = appState.getUser()
        text_username.text = user?.name
        profile_image.loadUrl(user?.avatar)
        adapter.add(ProfileMenuItem("Username", user?.username))
        adapter.add(ProfileMenuItem("Nama", user?.name, {
            showEditDialog(EditProfileDialog.NAME_TYPE)
        }))

        adapter.add(ProfileMenuItem("Email", user?.email, {
            showEditDialog(EditProfileDialog.EMAIL_TYPE)
        }))

        adapter.add(ProfileMenuItem("No HP", user?.noHp ?: "No HP belum ada", {
            showEditDialog(EditProfileDialog.PHONE_TYPE)
        }))

        adapter.add(ProfileMenuItem("Verifikasi", if (user?.verified == true) "Terverifikasi" else "Belum", {
            startActivityForResult(Intent(context, VerifyProfileActivity::class.java), 100)
        }))

        adapter.add(ProfileMenuItem("Pinjamin barang anda", "", {
            getBaseActivity().move(Intent(context, PinjaminActivity::class.java))
        }))

        adapter.add(ProfileMenuItem("Logout", "", {
            appState.logOut()
            getBaseActivity().move(Intent(context, PortalActivity::class.java), true, true)
        }))
    }

    private fun showEditDialog(type: Int) {
        val dialog = EditProfileDialog.createInstance(type)
        dialog.setTargetFragment(this, EditProfileDialog.EMAIL_TYPE)
        dialog.show(fragmentManager, "edit_profile")
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

    override fun onSuccess() {
        loadUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Refresh user regardless to requestCode & resultCOde
        loadUser()
    }

}