package com.setyongr.pinjamin.data.models

sealed class OrderStatus(val status: Int) {
    class Rejected: OrderStatus(-1)
    class Waiting: OrderStatus(0)
    class Accepted: OrderStatus(1)
    class Used: OrderStatus(2)
    class Completed: OrderStatus(3)

    companion object {
        fun build(status: Int?) : OrderStatus = when(status) {
            0 -> Waiting()
            1 -> Accepted()
            2 -> Used()
            3 -> Completed()
            else -> Rejected()
        }
    }
}
