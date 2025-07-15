package com.dede.oneshot.shot

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dede.oneshot.app.OneShotApp
import com.dede.oneshot.app.oneShotApp

class OneShotAppsManager private constructor(private val context: Context) {

    companion object {

        private const val TAG = "OneShotAppsManager"

        @SuppressLint("StaticFieldLeak")
        private val instance = OneShotAppsManager(oneShotApp)

        fun get(): OneShotAppsManager {
            return instance
        }
    }

    private inner class Preload : Runnable {
        override fun run() {
            for (shot in ALL_ONE_SHOT_LIST) {
                getAppInfoInternal(context, shot.appPackageName)
            }
        }
    }

    private inner class Remove(private val packageName: String) : Runnable {
        override fun run() {
            cachedAppInfoMap.remove(packageName)
        }
    }

    private inner class Refresh(private val packageName: String) : Runnable {
        override fun run() {
            val oneShot = ALL_ONE_SHOT_LIST.find { it.appPackageName == packageName }
            if (oneShot != null) {
                cachedAppInfoMap.remove(packageName)
                getAppInfoInternal(context, oneShot.appPackageName)
            }
        }
    }

    fun isAppInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun setup() {
        // todo Check the privacy policy
        if (false) {
            return
        }

        OneShotApp.asyncHandler.post(Preload())

        val intentFilter = IntentFilter().apply {
            addDataScheme("package")
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
        }
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val packageName = intent.data?.schemeSpecificPart
                if (packageName.isNullOrEmpty()) {
                    return
                }
                Log.i(TAG, "action: %s, package: %s".format(intent.action, packageName))
                val runnable = when (intent.action) {
                    Intent.ACTION_PACKAGE_ADDED,
                    Intent.ACTION_PACKAGE_REPLACED -> {
                        Refresh(packageName)
                    }
                    Intent.ACTION_PACKAGE_REMOVED -> {
                        Remove(packageName)
                    }
                    else -> return
                }
                runnable.run()
            }
        }
        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            null,
            OneShotApp.asyncHandler,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    internal data class Info(
        val name: CharSequence,
        val icon: Drawable,
    )

    private val cachedAppInfoMap = HashMap<String, Info>()

    private fun getAppInfoInternal(context: Context, packageName: String?): Info? {
        if (packageName.isNullOrEmpty()) {
            return null
        }
        val item = cachedAppInfoMap[packageName]
        if (item != null) {
            return item
        }

        val packageManager = context.packageManager
        val info = try {
            packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return null
        }
        return Info(
            name = packageManager.getApplicationLabel(info),
            icon = packageManager.getApplicationIcon(info)
        ).also {
            cachedAppInfoMap[packageName] = it
        }
    }

    fun getAppIcon(packageName: String?, @DrawableRes fallbackResId: Int): Drawable {
        val appInfo = getAppInfoInternal(context, packageName)
            ?: return checkNotNull(ActivityCompat.getDrawable(context, fallbackResId))
        return appInfo.icon
    }

    fun getAppName(packageName: String?, @StringRes fallbackResId: Int): CharSequence {
        val appInfo = getAppInfoInternal(context, packageName)
            ?: return context.getString(fallbackResId)
        return appInfo.name
    }
}
