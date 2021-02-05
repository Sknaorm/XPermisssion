package com.cn.work.base

interface WorkEmitter<T> {
    fun onNext(t: T)
    fun onError(t: Throwable)
    fun onComplete()
}