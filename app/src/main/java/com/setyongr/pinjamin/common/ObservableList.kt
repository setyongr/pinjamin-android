package com.setyongr.pinjamin.common

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ObservableList<T> {
    private val list: ArrayList<T> = ArrayList()
    private val publisher = PublishSubject.create<Event<T>>()

    fun add(value: T){
        list.add(value)
        publisher.onNext(Event.Added(value))
    }

    fun remove(value: T){
        list.remove(value)
        publisher.onNext(Event.Removed(value))
    }

    fun clear(){
        list.clear()
    }

    fun getData(): ArrayList<T> {
        return list
    }

    // first observable, only first fetch
    fun getObservableIterable(): Observable<T> {
        return Observable.fromIterable(list)
    }

    // publisher object observable
    fun getPublishObservable(): Observable<Event<T>> {
        return publisher
    }
}