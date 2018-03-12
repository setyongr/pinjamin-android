package com.setyongr.pinjamin.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class BaseActivity: AppCompatActivity() {
    fun move(intent: Intent, finishActivity: Boolean = false, newTask: Boolean = false) {
        if (newTask) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        startActivity(intent)

        if (finishActivity) {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}