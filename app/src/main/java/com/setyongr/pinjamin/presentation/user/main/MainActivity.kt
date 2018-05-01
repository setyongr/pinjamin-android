package com.setyongr.pinjamin.presentation.user.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseActivity
import com.setyongr.pinjamin.presentation.user.main.home.HomeFragment
import com.setyongr.pinjamin.presentation.user.main.order.OrderFragment
import com.setyongr.pinjamin.presentation.user.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_order -> {
                    loadFragment(OrderFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun loadFragment(f: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, f)
                .commit()
    }
}
