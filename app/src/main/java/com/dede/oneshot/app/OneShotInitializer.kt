package com.dede.oneshot.app

import android.content.Context
import androidx.startup.Initializer
import com.dede.oneshot.shot.OneShotAppsManager

lateinit var oneShotApp: OneShotApp
    private set

class OneShotInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        oneShotApp = context.applicationContext as OneShotApp
        OneShotAppsManager.get().setup()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}