package com.setyongr.pinjamin.common

sealed class Event<T>() {
    class Added<T>(val data: T): Event<T>()
    class Removed<T>(val data: T): Event<T>()
    class Updated<T>(val data: T): Event<T>()
}
