package com.setyongr.pinjamin.presentation.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.common.inflate
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.presentation.pinjamin.OrderToMeDetailActivity
import kotlinx.android.synthetic.main.item_order.view.*

class OrderToMeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableMapOf<Int, ResponseModel.Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_order))
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data.values.toList()[position])
    }

    fun add(order: ResponseModel.Order) {
        data[order.id] = order
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind(data: ResponseModel.Order) = with(itemView) {
            name_text.text= data.pinjam.name
            accepted_text.text = when(data.status) {
                0 -> "Waiting"
                1 -> "Accepted"
                else -> "Rejected"
            }
            itemView.setOnClickListener {
                val intent = Intent(context, OrderToMeDetailActivity::class.java)
                intent.putExtra("id", data.id)
                context.startActivity(intent)
            }
        }
    }

}