package com.dede.oneshot.app

import android.app.Application
import android.os.HandlerThread
import androidx.core.os.HandlerCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class OneShotApp : Application() {

    companion object {
        val cachedExecutor: ExecutorService by lazy { Executors.newCachedThreadPool() }

        val asyncHandler by lazy {
            val handleThread = HandlerThread("Async-Handler-Thread")
            handleThread.start()
            HandlerCompat.createAsync(handleThread.looper)
        }
    }

}