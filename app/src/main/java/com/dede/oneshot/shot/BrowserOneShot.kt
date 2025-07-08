package com.dede.oneshot.shot

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import com.dede.oneshot.R

class BrowserOneShot : OneShot {

    override val appNameFallbackResId: Int = R.string.app_name_browser

    override val appPackageName: String = "com.android.browser"

    override fun buildIntent(context: Context, keyword: String): Intent {
        return Intent(Intent.ACTION_WEB_SEARCH)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .putExtra(SearchManager.QUERY, keyword)
    }
}