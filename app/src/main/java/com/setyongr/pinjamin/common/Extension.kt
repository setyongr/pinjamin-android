package com.setyongr.pinjamin.common

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import io.reactivex.Observable
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.setyongr.pinjamin.base.BaseActivity
import io.reactivex.Completable
import okhttp3.ResponseBody


fun <T> Observable<T>.applyDefaultSchedulers(provider: SchedulerProvider) =
        this.subscribeOn(provider.io()).observeOn(provider.ui())

fun Completable.applyDefaultSchedulers(provider: SchedulerProvider) =
        this.subscribeOn(provider.io()).observeOn(provider.ui())


fun CharSequence.isEmailText(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Fragment.getBaseActivity() = this.activity as BaseActivity

fun ImageView.loadUrl(url: String?) {
    if(!TextUtils.isEmpty(url) && url != null) {
        Glide.with(context)
                .load(url)
                .into(this)
    }
}

inline fun <reified T> ResponseBody.parse(): T {
    val adapter = Gson()
    return adapter.fromJson(this.string(), T::class.java)
}