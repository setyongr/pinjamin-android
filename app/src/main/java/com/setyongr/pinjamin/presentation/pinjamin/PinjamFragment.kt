package com.setyongr.pinjamin.presentation.pinjamin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseFragment
import com.setyongr.pinjamin.common.getBaseActivity
import com.setyongr.pinjamin.presentation.pinjamin.addpinjaman.AddPinjamanFragment
import com.setyongr.pinjamin.presentation.pinjamin.mypinjaman.MyPinjamanFragment
import com.setyongr.pinjamin.presentation.pinjamin.ordertome.OrderToMeFragment
import kotlinx.android.synthetic.main.fragment_pinjam.*

class PinjamFragment: BaseFragment() {
    val fragments by lazy {
        mapOf<String, Fragment>(
                "Pinjamkan" to AddPinjamanFragment(),
                "Pinjamanku" to MyPinjamanFragment(),
                "Orderku" to OrderToMeFragment()
        )
    }

    override fun getLayout(): Int = R.layout.fragment_pinjam

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBaseActivity().supportActionBar?.title = "My Pinjamin"

        viewpager.adapter = TicketPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewpager)
    }

    inner class TicketPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getPageTitle(position: Int) = fragments.toList()[position].first

        override fun getItem(position: Int) : Fragment =
                fragments.toList()[position].second

        override fun getCount() = fragments.size
    }

}