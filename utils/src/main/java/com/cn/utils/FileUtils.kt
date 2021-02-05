package com.cn.utils

import java.io.File

object FileUtils {
    fun createFile(file: File?): Boolean {
        file ?: return false
        if (file.isDirectory) {
            if (!file.exists()) {
                return file.mkdirs()
            }
            return true
        } else if (file.isFile) {
            if (!file.exists()) {
                createFile(file.parentFile)
                return file.createNewFile()
            }
            return true
        }
        return false
    }

    fun copyFile(file: File, newPath: String, overwrite: Boolean = true): File {
        val newFile = File(newPath)
        val createFile = createFile(file)
        return if (createFile) {
            file.copyTo(newFile, overwrite)
        } else {
            newFile
        }
    }


}







