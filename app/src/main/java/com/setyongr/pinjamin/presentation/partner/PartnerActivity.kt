package com.setyongr.pinjamin.presentation.partner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.partner.pinjaman.PartnerPinjamanFragment
import com.setyongr.pinjamin.presentation.partner.order.PartnerOrderFragment
import kotlinx.android.synthetic.main.activity_pinjamin.*
import java.util.*
import javax.inject.Inject

class PartnerActivity: BaseInjectedActivity() {
    val fragments by lazy {
        mapOf<String, Fragment>(
                "Pinjamanku" to PartnerPinjamanFragment(),
                "Orderku" to PartnerOrderFragment()
        )
    }

    @Inject
    lateinit var appState: AppState

    private var broadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinjamin)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pinjamin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewpager.adapter = PinjamPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager)
    }

    override fun onStart() {
        super.onStart()

        // Get updateing time
        if (!appState.latestTime.hasValue()) {
            appState.latestTime.onNext(Date())
        }

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                if (intent?.action?.compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    appState.latestTime.onNext(Date())
                }
            }

        }

        registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onStop() {
        super.onStop()
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    inner class PinjamPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getPageTitle(position: Int) = fragments.toList()[position].first

        override fun getItem(position: Int) : Fragment =
                fragments.toList()[position].second

        override fun getCount() = fragments.size
    }
}