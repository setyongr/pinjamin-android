package com.setyongr.pinjamin.presentation.partner.pinjaman

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.adapter.MyPinjamanAdapter
import com.setyongr.pinjamin.presentation.partner.createpinjaman.CreatePinjamanActivity
import kotlinx.android.synthetic.main.fragment_partner_pinjaman.*
import javax.inject.Inject

class PartnerPinjamanFragment: BaseInjectedFragment(), PartnerPinjamanView {

    @Inject
    lateinit var mPresenter: PartnerPinjamanPresenter

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

    override fun getLayout(): Int = R.layout.fragment_partner_pinjaman

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
            context?.startActivity(Intent(context, CreatePinjamanActivity::class.java))
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

    override fun addPinjaman(pinjaman: Pinjaman) {
        adapter.add(pinjaman)
    }

}
