package com.setyongr.pinjamin.presentation.pinjamin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.presentation.pinjamin.addpinjaman.AddPinjamanFragment
import com.setyongr.pinjamin.presentation.pinjamin.mypinjaman.MyPinjamanFragment
import com.setyongr.pinjamin.presentation.pinjamin.ordertome.OrderToMeFragment
import kotlinx.android.synthetic.main.activity_pinjamin.*

class PinjaminActivity: BaseActivity() {
    val fragments by lazy {
        mapOf<String, Fragment>(
                "Pinjamkan" to AddPinjamanFragment(),
                "Pinjamanku" to MyPinjamanFragment(),
                "Orderku" to OrderToMeFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinjamin)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pinjamin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewpager.adapter = PinjamPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager)
    }

    inner class PinjamPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getPageTitle(position: Int) = fragments.toList()[position].first

        override fun getItem(position: Int) : Fragment =
                fragments.toList()[position].second

        override fun getCount() = fragments.size
    }
}