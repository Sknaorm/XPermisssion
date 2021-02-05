package com.cn.http.work.expand

import java.io.InputStream
import java.io.OutputStream

class DownBuilder {
    var url: String? = null
    var delayCallBackTime = 1000
    var contentLength: Long? = null
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null
    var outFilePath: String? = null
}