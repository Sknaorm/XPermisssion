package com.cn.http.work.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


abstract class WorkObservable<T> {
    internal var mNextCoroutineContext: CoroutineContext? = null
    fun nextOn(workThread: WorkThread) = apply {
        mNextCoroutineContext = workThread.coroutineContext
    }

    fun workOn(workThread: WorkThread) = apply {
        workOn(this, workThread.coroutineContext)
    }

    fun <R> map(block: (T) -> R): WorkObservable<R> =
        WorkObservableMap(this, block)

    fun <R> switchMap(block: (T) -> WorkObservable<R>): WorkObservable<R> =
        WorkObservableSwitchMap(this, block)

    private fun workOn(
        observable: WorkObservable<*>,
        coroutineContext: CoroutineContext
    ) {
        if (observable is WorkObservableCreate<*>) {
            observable.mWorkCoroutineContext = coroutineContext
        } else if (observable is WorkObservableMap<*, *>) {
            workOn(observable.source, coroutineContext)
        }
    }

    protected fun doSome(coroutineScope: CoroutineScope, block: () -> Unit) {
        mNextCoroutineContext?.apply {
            coroutineScope.launch(context = this) {
                block()
            }
        } ?: block()
    }


    fun doOnStart(coroutineScope: CoroutineScope, onStart: (() -> Unit)) =
        observe(coroutineScope, onStart = onStart)

    fun doOnNext(coroutineScope: CoroutineScope, onNext: ((T) -> Unit)) =
        observe(coroutineScope, onNext = onNext)

    fun doOnError(coroutineScope: CoroutineScope, onError: ((Throwable) -> Unit)) =
        observe(coroutineScope, onError = onError)

    fun doOnComplete(coroutineScope: CoroutineScope, onComplete: (() -> Unit))=
        observe(coroutineScope, onComplete = onComplete)


    fun observe(
        coroutineScope: CoroutineScope,
        onStart: (() -> Unit)? = null,
        onNext: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ): Job {
        return coroutineScope.launch {
            perform(coroutineScope, object : WorkObserver<T>() {
                override fun onStart() {
                    onStart?.invoke()
                }

                override fun onNext(t: T) {
                    onNext?.invoke(t)
                }

                override fun onError(t: Throwable) {
                    onError?.invoke(t)
                }

                override fun onComplete() {
                    onComplete?.invoke()
                }

            })
        }
    }

    abstract fun perform(coroutineScope: CoroutineScope, observer: WorkObserver<T>)
}