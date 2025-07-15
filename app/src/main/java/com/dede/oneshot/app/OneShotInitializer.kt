package com.dede.oneshot.app

import android.content.Context
import androidx.startup.Initializer
import com.dede.oneshot.shot.OneShot

class OneShotInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        OneShotApp.cachedExecutor.execute(OneShot.Preload(context))
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}