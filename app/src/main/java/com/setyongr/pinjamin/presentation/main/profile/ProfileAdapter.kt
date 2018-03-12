package com.setyongr.pinjamin.presentation.main.profile

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseRecyclerAdapter
import com.setyongr.pinjamin.common.inflate
import kotlinx.android.synthetic.main.item_profile.view.*

class ProfileAdapter: BaseRecyclerAdapter<ProfileMenuItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(parent.inflate(R.layout.item_profile))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as Holder).bind(items[position])
    }

    class Holder(v: View): RecyclerView.ViewHolder(v) {
        fun bind(item: ProfileMenuItem) = with(itemView) {
            text_title.text = item.title
            text_value.text = item.value

            if (item.onClick == null) {
                next_image.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                item.onClick?.invoke()
            }
        }
    }

}