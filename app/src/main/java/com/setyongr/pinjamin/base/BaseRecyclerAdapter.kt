package com.setyongr.pinjamin.base

import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var items: ArrayList<T> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}