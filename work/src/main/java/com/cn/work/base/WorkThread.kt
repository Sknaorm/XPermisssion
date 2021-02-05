package com.cn.http.work.base

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

enum class WorkThread(val coroutineContext: CoroutineContext) {
    MAIN(Dispatchers.Main),
    IO(Dispatchers.IO),
    DEFAULT(Dispatchers.Default),
    UNCONFINED(Dispatchers.Unconfined),
    EMPTY(kotlin.coroutines.EmptyCoroutineContext),
}