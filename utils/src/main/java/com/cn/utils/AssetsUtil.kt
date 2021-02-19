package com.cn.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AssetsUtil {

    private fun getInputStream(context: Context, fileName: String): InputStream? {
        val assetManager = context.assets
        return assetManager?.open(fileName)
    }

    fun getString(context: Context, fileName: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val inputStream = getInputStream(context, fileName)
            inputStream ?: return null
            val bf = BufferedReader(InputStreamReader(inputStream))
            var line: String
            bf.use {
                while ((it.readLine().apply { line = this }) != null) {
                    stringBuilder.append(line)
                }
            }
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun getBitmap(context: Context, fileName: String): Bitmap? {
        try {
            val inputStream = getInputStream(context, fileName)
            inputStream ?: return null
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            return null
        }
    }
}