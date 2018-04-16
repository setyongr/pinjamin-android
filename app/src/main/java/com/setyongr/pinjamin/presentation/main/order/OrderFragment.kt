package com.setyongr.pinjamin.presentation.main.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order.*
import javax.inject.Inject

class OrderFragment: BaseInjectedFragment(), OrderView {
    @Inject
    lateinit var mPresenter: OrderPresenter

    val adapter by lazy {
        OrderAdapter()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun getLayout(): Int = R.layout.fragment_order

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

    override fun addOrder(order: ResponseModel.Order) {
        if (no_order.visibility == View.VISIBLE) {
            no_order.visibility = View.GONE
        }

        adapter.add(order)
    }

}