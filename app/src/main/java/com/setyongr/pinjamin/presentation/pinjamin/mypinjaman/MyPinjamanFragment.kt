package com.setyongr.pinjamin.presentation.pinjamin.mypinjaman

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.adapter.MyPinjamanAdapter
import com.setyongr.pinjamin.presentation.pinjamin.addpinjaman.AddPinjamanActivity
import kotlinx.android.synthetic.main.fragment_my_pinjaman.*
import javax.inject.Inject

class MyPinjamanFragment: BaseInjectedFragment(), MyPinjamanView {

    @Inject
    lateinit var mPresenter: MyPinjamanPresenter

    val adapter by lazy {
        MyPinjamanAdapter()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.attachView(this)
        mPresenter.load()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun getLayout(): Int = R.layout.fragment_my_pinjaman

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.attachView(this)

        (activity as BaseActivity).supportActionBar?.title = "Pinjamin"

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        swiperefresh.setOnRefreshListener {
            adapter.clear()
            mPresenter.clear()
            mPresenter.getRemote()
        }

        mPresenter.load()

        fab.setOnClickListener {
            context?.startActivity(Intent(context, AddPinjamanActivity::class.java))
        }
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showLoading(status: Boolean) {
        swiperefresh.isRefreshing = status
    }

    override fun showError(e: Throwable) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

    override fun addPinjaman(pinjaman: ResponseModel.Pinjaman) {
        adapter.add(pinjaman)
    }

}
