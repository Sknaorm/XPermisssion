package com.cn.http.work.base

import com.cn.work.base.WorkObservable
import com.cn.work.base.WorkObserver
import kotlinx.coroutines.CoroutineScope


class WorkObservableMap<T, R>(val source: WorkObservable<T>, val block: (T) -> R) :
    WorkObservable<R>() {
    override fun perform(coroutineScope: CoroutineScope, observer: WorkObserver<R>) {
        source.perform(coroutineScope, WorkObserverMap(coroutineScope, block, observer))
    }


    inner class WorkObserverMap<T, R>(
        val coroutineScope: CoroutineScope,
        val block: (T) -> R,
        val observer: WorkObserver<R>
    ) : WorkObserver<T>() {
        override fun onStart() {
            doSome(coroutineScope) {
                observer.onStart()
            }
        }

        override fun onNext(t: T) {
            var r: R? = null
            var exception: Exception? = null
            try {
                r = block(t)
            } catch (e: Exception) {
                exception = e
            }
            r?.let {
                doSome(coroutineScope) {
                    observer.onNext(it)
                }
            } ?: exception?.let {
                onError(it)
            }

        }

        override fun onError(t: Throwable) {
            doSome(coroutineScope) {
                observer.onError(t)
            }

        }

        override fun onComplete() {
            doSome(coroutineScope) {
                observer.onComplete()
            }

        }
    }
}