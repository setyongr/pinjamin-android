package com.setyongr.pinjamin.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseDelegatedRecyclerAdapter: BaseRecyclerAdapter<ViewType>() {

    private var delegatedAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatedAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatedAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].getViewType()
    }

    fun addDelegateAdapter(key: Int, adapter: ViewTypeDelegateAdapter) {
        delegatedAdapters.put(key, adapter)
    }
}