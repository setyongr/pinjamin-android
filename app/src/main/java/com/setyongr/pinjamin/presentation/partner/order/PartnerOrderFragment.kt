package com.setyongr.pinjamin.presentation.partner.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.base.BaseInjectedFragment
import com.setyongr.domain.model.Order
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.adapter.OrderToMeAdapter
import kotlinx.android.synthetic.main.fragment_order_to_me.*
import javax.inject.Inject

class PartnerOrderFragment: BaseInjectedFragment(), PartnerOrderView {

    @Inject
    lateinit var mPresenter: PartnerOrderPresenter

    @Inject
    lateinit var adapter: OrderToMeAdapter

    override fun onResume() {
        super.onResume()
        mPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun getLayout(): Int = R.layout.fragment_order_to_me

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.attachView(this)

        (activity as BaseActivity).supportActionBar?.title = "Pinjamin"

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        adapter.clear()
        mPresenter.clear()

        swiperefresh.setOnRefreshListener {
            adapter.clear()
            mPresenter.clear()
            mPresenter.getRemote()
        }

        mPresenter.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clear()
        mPresenter.clear()
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

    override fun addOrder(order: Order) {
        adapter.add(order)
    }

}
