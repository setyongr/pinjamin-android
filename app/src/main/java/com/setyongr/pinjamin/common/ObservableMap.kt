package com.setyongr.pinjamin.common

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ObservableMap<K, V> {
    private val map = mutableMapOf<K, V>()
    private val publisher = PublishSubject.create<Event<Pair<K, V>>>()

    fun add(key: K, value: V){
        map[key] = value
        publisher.onNext(Event.Added(Pair(key, value)))
    }

    fun remove(key: K){
        val valueTmp = map[key]
        if (valueTmp != null) {
            map.remove(key)
            publisher.onNext(Event.Removed(Pair(key, valueTmp)))
        }
    }

    fun getByKey(key: K): V?{
        return map[key]
    }

    fun update(key: K, value: V){
        map[key] = value
        publisher.onNext(Event.Updated(Pair(key, value)))
    }

    fun updateWithoutPublish(key: K, value: V){
        map[key] = value
    }

    fun replace(ids: List<K>, items: List<V>){
        map.clear()
        if (ids.size != items.size){
            throw IllegalArgumentException()
        }

        for (idx in 0 until ids.size){
            val id = ids[idx]
            map[id] = items[idx]
        }
    }

    fun clear(){
        map.clear()
    }

    fun getListData(): List<V> {
        return map.values.toList()
    }

    // first observable, only first fetch
    fun getObservableIterable(): Observable<V> {
        return Observable.fromIterable(getListData())
    }

    // publisher object observable
    fun getPublishObservable(): PublishSubject<Event<Pair<K, V>>> {
        return publisher
    }
}