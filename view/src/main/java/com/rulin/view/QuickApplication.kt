package com.rulin.view

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class QuickApplication : Application(), Application.ActivityLifecycleCallbacks {
    abstract fun init()
    override fun onCreate() {
        super.onCreate()
        this.registerActivityLifecycleCallbacks(this)
        init()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}