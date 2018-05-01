package com.setyongr.pinjamin.presentation.user.main.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.presentation.adapter.PinjamanAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment: BaseInjectedFragment(), HomeView {
    @Inject
    lateinit var mPresenter: HomePresenter

    val adapter by lazy {
        PinjamanAdapter()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.attachView(this)
        adapter.clear()
        mPresenter.load()

    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun getLayout(): Int = R.layout.fragment_home

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