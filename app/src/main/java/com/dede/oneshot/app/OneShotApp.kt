package com.dede.oneshot.app

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class OneShotApp : Application() {

    companion object {
        val cachedExecutor: ExecutorService by lazy { Executors.newCachedThreadPool() }
    }

}