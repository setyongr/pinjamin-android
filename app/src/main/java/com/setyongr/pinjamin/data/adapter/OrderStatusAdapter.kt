package com.setyongr.pinjamin.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.setyongr.pinjamin.data.models.OrderStatus

class OrderStatusAdapter: TypeAdapter<OrderStatus>() {
    override fun write(out: JsonWriter?, value: OrderStatus?) {
        if (value == null) {
            out?.nullValue()
            return
        }
        out?.value(value.status)
    }

    override fun read(`in`: JsonReader): OrderStatus {
        if (`in`.peek() == JsonToken.NULL) {
            return OrderStatus.build(-1)
        }

        return OrderStatus.build(`in`.nextInt())
    }
}