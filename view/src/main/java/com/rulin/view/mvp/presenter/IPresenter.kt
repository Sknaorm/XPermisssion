package com.rulin.view.mvp.presenter

import android.os.Bundle
import com.rulin.view.mvp.view.IView

interface IPresenter<V:IView> {
    fun onCreate(savedInstanceState: Bundle?) {
    }

    fun onStart() {
    }

    fun onResume() {
    }

    fun onRestart() {
    }

    fun onPause() {
    }

    fun onDestroy() {
    }
}