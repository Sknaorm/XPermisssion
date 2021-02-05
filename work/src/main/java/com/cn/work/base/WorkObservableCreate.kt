package com.cn.http.work.base

import com.cn.work.base.WorkEmitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class WorkObservableCreate<T>(val block: (WorkEmitter<T>) -> Unit) : WorkObservable<T>() {

    var mWorkCoroutineContext: CoroutineContext? = null

    fun doCreate(coroutineScope: CoroutineScope, block: () -> Unit) {
        mWorkCoroutineContext?.apply {
            coroutineScope.launch(context = this) {
                block()
            }
        } ?: block()
    }

    override fun perform(coroutineScope: CoroutineScope, observer: WorkObserver<T>) {
        doCreate(coroutineScope) {
            observer.onStart()
            block(Emitter(coroutineScope, observer))
        }
    }

    inner class Emitter<T>(val coroutineScope: CoroutineScope, val observer: WorkObserver<T>) :
        WorkEmitter<T> {

        override fun onNext(t: T) {
            doSome(coroutineScope) {
                observer.onNext(t)
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