package com.cn.work.expand

import com.cn.work.base.WorkEmitter
import com.cn.work.base.WorkObservable
import com.cn.http.work.base.WorkObservableCreate
import com.cn.work.base.WorkThread
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

fun <T> doWork(block: (WorkEmitter<T>) -> Unit): WorkObservable<T> =
    WorkObservableCreate(block)

fun <T> doOnlyWork(vararg t: T): WorkObservable<T> =
    WorkObservableCreate {
        try {
            t.forEach { item ->
                it.onNext(item)
            }
            it.onComplete()
        } catch (e: Exception) {
            it.onError(e)
        }
    }

fun doDownloadWork(block: DownBuilder.() -> Unit) =
    doWork<Number> {
        val builder = DownBuilder().apply(block)
        var startTime = System.currentTimeMillis()
        val decimalFormat = DecimalFormat("00.0")
        try {
            builder.url?.apply {
                val connection = URL(this).openConnection() as HttpURLConnection
                builder.inputStream = connection.inputStream
                builder.contentLength = connection.contentLength.toLong()
            }
            builder.outFilePath?.apply {
                val file = File(this)
                if (!file.exists()) {
//                    FileUtils.createFile(file.absolutePath)
                    it.onError(NullPointerException("file cannot be null"))
                }
                builder.outputStream = file.outputStream()
            }
            checkNotNull(builder.inputStream)
            checkNotNull(builder.contentLength)
            var len: Int
            var total = 0
            val bytes = ByteArray(1024)
            builder.inputStream?.use { input ->
                builder.outputStream?.use { output ->
                    while (input.read(bytes).apply { len = this } != -1) {
                        total += len
                        output.write(bytes, 0, len)
                        val f = total.toFloat() / checkNotNull(builder.contentLength)
                        val currentTimeMillis = System.currentTimeMillis()
                        if (currentTimeMillis - startTime > builder.delayCallBackTime) {
                            startTime = currentTimeMillis
                            it.onNext(decimalFormat.format(f).toFloat())
                        }
                    }
                    it.onComplete()
                }
            }
        } catch (e: Exception) {
            it.onError(e)
        }
    }.workOn(WorkThread.IO)
