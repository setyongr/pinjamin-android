package com.setyongr.pinjamin.presentation.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.common.inflate
import com.setyongr.domain.model.Pinjaman
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.presentation.partner.pinjamandetail.PartnerPinjamanDetailActivity
import kotlinx.android.synthetic.main.item_pinjaman.view.*

class MyPinjamanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableMapOf<Int, Pinjaman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_pinjaman))
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(data.values.toList()[position])
    }

    fun add(pinjaman: Pinjaman) {
        data[pinjaman.id] = pinjaman
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind(data: Pinjaman) = with(itemView) {
            name_text.text = data.name
            user_text.text = "By ${data.user.username}"
            sewa_image.loadUrl(data.image)

            itemView.setOnClickListener {
                val intent = Intent(context, PartnerPinjamanDetailActivity::class.java)
                intent.putExtra("id", data.id)
                context.startActivity(intent)
            }
        }
    }

}