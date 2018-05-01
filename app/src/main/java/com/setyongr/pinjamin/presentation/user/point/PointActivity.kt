package com.setyongr.pinjamin.presentation.user.point

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import kotlinx.android.synthetic.main.activity_point.*

class PointActivity: BaseInjectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val point = intent.getIntExtra("point", 0)
        point_text.text = "${point} Point"

        if (point < 250) {
            btn_redeem.isEnabled = false
        }
    }

    companion object {
        fun open(context: Context, point: Int) {
            val intent = Intent(context, PointActivity::class.java)
            intent.putExtra("point", point)
            context.startActivity(intent)
        }
    }

}