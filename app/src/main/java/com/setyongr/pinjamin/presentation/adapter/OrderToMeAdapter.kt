package com.setyongr.pinjamin.presentation.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.common.inflate
import com.setyongr.domain.model.OrderStatus
import com.setyongr.domain.model.Order
import com.setyongr.pinjamin.presentation.pinjamin.OrderToMeDetailActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_order.view.*
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import javax.inject.Inject

class OrderToMeAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_order))
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    fun add(order: Order) {
        data.add(order)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {

        var disposable: Disposable? = null

        fun bind(data: Order) = with(itemView) {
            name_text.text= data.user.name
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
                is OrderStatus.Used -> {
                    side_view.setBackgroundResource(R.color.colorLine)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorLine))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.black_circle, 0, 0, 0)
                    "Used"
                }
                is OrderStatus.Completed -> {
                    side_view.setBackgroundResource(R.color.colorFacebook)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorFacebook))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.blue_circle, 0, 0, 0)
                    "Completed"
                }
                else -> {
                    side_view.setBackgroundResource(R.color.colorRed)
                    accepted_text.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
                    accepted_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0)
                    "Rejected"
                }
            }

            itemView.setOnClickListener {
                val intent = Intent(context, OrderToMeDetailActivity::class.java)
                intent.putExtra("id", data.id)
                context.startActivity(intent)
            }

            data.crated_at?.let {
                val hasMilis = it.contains(".", true)
                if (hasMilis) {
                    time_text.text = "Dibuat: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTime().parseLocalDateTime(it))
                } else {
                    time_text.text = "Dibuat: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime(it))
                }
            }

            data.used_at?.let {
                time_text2.visibility = View.VISIBLE
                val hasMilis = it.contains(".", true)
                if (hasMilis) {
                    time_text2.text = "Dipakai: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTime().parseLocalDateTime(it))
                } else {
                    time_text2.text = "Dipakai: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime(it))
                }
            }

            data.finished_at?.let {
                time_text3.visibility = View.VISIBLE
                val hasMilis = it.contains(".", true)
                if (hasMilis) {
                    time_text3.text = "Selesai: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTime().parseLocalDateTime(it))
                } else {
                    time_text3.text = "Selesai: " + DateTimeFormat.forPattern("dd/MM/YYYY HH:mm").print(ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime(it))
                }
            }

//            itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
//                override fun onViewDetachedFromWindow(p0: View?) {
//                    if (disposable != null) {
//                        disposable?.dispose()
//                        disposable = null
//                    }
//                }
//
//                override fun onViewAttachedToWindow(p0: View?) {
//                    disposable = appState.latestTime.applyDefaultSchedulers(schedulerProvider)
//                            .subscribe {
//                                time_text.text = SimpleDateFormat("HH:mm:ss").format(it)
//                            }
//                }
//            })
        }
    }

}