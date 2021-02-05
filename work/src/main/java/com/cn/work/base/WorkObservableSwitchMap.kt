package com.cn.http.work.base

import kotlinx.coroutines.CoroutineScope


class WorkObservableSwitchMap<T, R>(
    val source: WorkObservable<T>,
    val block: (T) -> WorkObservable<R>
) : WorkObservable<R>() {
    override fun perform(coroutineScope: CoroutineScope, observer: WorkObserver<R>) {
        source.perform(coroutineScope, WorkObserverSwitchMap(coroutineScope, block, observer))
    }


    inner class WorkObserverSwitchMap<T, R>(
        val coroutineScope: CoroutineScope,
        val block: (T) -> WorkObservable<R>,
        val observer: WorkObserver<R>
    ) : WorkObserver<T>() {
        override fun onStart() {
            observer.onStart()
        }

        override fun onNext(t: T) {
            var r: WorkObservable<R>? = null
            var exception: Exception? = null
            try {
                r = block(t)
            } catch (e: Exception) {
                exception = e
            }
            r?.perform(coroutineScope, object : WorkObserver<R>() {
                override fun onStart() {
                }

                override fun onNext(t: R) {
                    doSome(coroutineScope) {
                        observer.onNext(t)
                    }
                }

                override fun onError(t: Throwable) {
                    this@WorkObserverSwitchMap.onError(t)
                }

                override fun onComplete() {
                }

            }) ?: exception?.let { onError(it) }
        }

        override fun onError(t: Throwable) {
            observer.onError(t)

        }

        override fun onComplete() {
            observer.onComplete()

        }
    }
}