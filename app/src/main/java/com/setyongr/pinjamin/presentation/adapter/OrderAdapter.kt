package com.setyongr.pinjamin.presentation.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.common.inflate
import com.setyongr.pinjamin.common.loadUrl
import com.setyongr.pinjamin.data.models.OrderStatus
import com.setyongr.pinjamin.data.models.ResponseModel
import com.setyongr.pinjamin.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.item_order.view.*
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat

class OrderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<ResponseModel.Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_order))
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    fun add(order: ResponseModel.Order) {
        data.add(order)
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
                is OrderStatus.Waiting -> {
                    side_view.setBackgroundResource(R.color.colorOrange)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorOrange))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.orange_circle, 0, 0, 0)
                    "Waiting"
                }
                is OrderStatus.Accepted -> {
                    side_view.setBackgroundResource(R.color.colorGreen)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorGreen))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0)
                    "Accepted"
                }
                else -> {
                    side_view.setBackgroundResource(R.color.colorRed)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0)
                    "Rejected"
                }
            }

            data.crated_at?.let {
                time_text.text = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTime().parseDateTime(data.crated_at))
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", data.pinjam.id)
                intent.putExtra("hide_order", true)
                context.startActivity(intent)
            }
        }
    }

}