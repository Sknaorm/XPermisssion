package com.cn.http.work.base


abstract class WorkObserver<T> {
    abstract  fun onStart()
    abstract  fun onNext(t: T)
    abstract  fun onError(t: Throwable)
    abstract  fun onComplete()
}