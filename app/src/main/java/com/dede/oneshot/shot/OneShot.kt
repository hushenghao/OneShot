package com.dede.oneshot.shot

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.dede.oneshot.R
import java.text.MessageFormat


val ALL_ONE_SHOT_LIST = listOf(
    // 社交
    buildOneShot(
        appPackageName = "com.xingin.xhs",
        appNameFallbackResId = R.string.app_name_xhs,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "xhsdiscover://search/result?keyword={0}",
    ),
//    buildOneShot(
//        appPackageName = "com.tencent.mm",
//        appNameFallbackResId = R.string.app_name_wechat,
//        dataPattern = "weixin://dl/business/search?query={0}",
//    ),
    buildOneShot(
        appPackageName = "com.sina.weibo",
        appNameFallbackResId = R.string.app_name_weibo,
        dataPattern = "sinaweibo://searchall?q={0}",
    ),

    // 资讯
    buildOneShot(
        appPackageName = "com.zhihu.android",
        appNameFallbackResId = R.string.app_name_zhihu,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "zhihu://search?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.douban.frodo",
        appNameFallbackResId = R.string.app_name_douban,
        dataPattern = "douban://douban.com/search/result?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.ss.android.article.news",
        appNameFallbackResId = R.string.app_name_toutiao,
        dataPattern = "snssdk141://search?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.netease.newsreader.activity",
        appNameFallbackResId = R.string.app_name_netease_news,
        dataPattern = "newsapp://nc/search?word={0}",
    ),

    // 视频
    buildOneShot(
        appPackageName = "tv.danmaku.bili",
        appNameFallbackResId = R.string.app_name_bilibili,
        dataPattern = "bilibili://search?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.smile.gifmaker",
        appNameFallbackResId = R.string.app_name_ks,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "kwai://search?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.ss.android.ugc.aweme",
        appNameFallbackResId = R.string.app_name_douyin,
        dataPattern = "snssdk1128://search?keyword={0}",
    ),

    // 音乐
//    buildOneShot(
//        appPackageName = "com.netease.cloudmusic",
//        appNameFallbackResId = R.string.app_name_netease,
//        dataPattern = "orpheus://search?q={0}",
//    ),
//    buildOneShot(
//        appPackageName = "com.tencent.qqmusic",
//        appNameFallbackResId = R.string.app_name_qqmusic,
//        dataPattern = "qqmusic://qq.com/qqmusic_search?p={0}",
//    ),

    // 生活服务
    buildOneShot(
        appPackageName = "com.sankuai.meituan",
        appNameFallbackResId = R.string.app_name_meituan,
        dataPattern = "imeituan://www.meituan.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.sankuai.meituan.takeoutnew",
        appNameFallbackResId = R.string.app_name_meituanwaimai,
        dataPattern = "meituanwaimai://waimai.meituan.com/search?query={0}",
    ),
    buildOneShot(
        appPackageName = "me.ele",
        appNameFallbackResId = R.string.app_name_eleme,
        dataPattern = "eleme://search?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.dianping.v1",
        appNameFallbackResId = R.string.app_name_dianping,
        dataPattern = "dianping://searchshoplist?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.eg.android.AlipayGphone",
        appNameFallbackResId = R.string.app_name_alipay,
        dataPattern = "alipays://platformapi/startapp?appId=20001003&keyword={0}",
    ),

    // 地图
    buildOneShot(
        appPackageName = "com.autonavi.minimap",
        appNameFallbackResId = R.string.app_name_amap,
        dataPattern = "amapuri://search/general?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.baidu.BaiduMap",
        appNameFallbackResId = R.string.app_name_baidumap,
        dataPattern = "baidumap://map/place/search?query={0}",
    ),

    // 购物
    buildOneShot(
        appPackageName = "com.taobao.taobao",
        appNameFallbackResId = R.string.app_name_taobao,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "taobao://m.taobao.com/tbopen/index.html?h5Url=http://s.taobao.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.tmall.wireless",
        appNameFallbackResId = R.string.app_name_tmall,
        dataPattern = "tmall://page.tm/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.xunmeng.pinduoduo",
        appNameFallbackResId = R.string.app_name_pdd,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "pinduoduo://com.xunmeng.pinduoduo/search_result.html?search_key={0}"
    ),
    buildOneShot(
        appPackageName = "com.jingdong.app.mall",
        appNameFallbackResId = R.string.app_name_jd,
        // {"des":"productList","keyWord":"{query}","from":"search","category":"jump"}
        dataPattern = "openjd://virtual?params=%7B%22des%22%3A%22productList%22%2C%22keyWord%22%3A%22{0}%22%2C%22from%22%3A%22search%22%2C%22category%22%3A%22jump%22%7D",
    ),
    buildOneShot(
        appPackageName = "com.taobao.idlefish",
        appNameFallbackResId = R.string.app_name_idlefish,
        dataPattern = "fleamarket://searchresult?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.achievo.vipshop",
        appNameFallbackResId = R.string.app_name_vipshop,
        dataPattern = "vipshop://routeTo?url=productlist%2Fsearch_product_list%3Fkeyword%3D{0}",
    ),

    // 搜索
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_bing,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.bing.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_baidu,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.baidu.com/s?wd={0}",
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_google,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.google.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_market,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "market://search?q={0}",
    ),
    BrowserOneShot(),
)

fun String.encodeKeyword(): String {
    return Uri.encode(this)
}

fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        packageManager.getApplicationInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun buildOneShot(
    appPackageName: String,
    @StringRes appNameFallbackResId: Int,
    action: String = Intent.ACTION_VIEW,
    category: String = Intent.CATEGORY_DEFAULT,
    dataPattern: String? = null,// scheme://host/path?search={0}
    keywordEncode: Boolean = true,
    matchPackage: Boolean = false,
): OneShot {
    return object : OneShot {
        override val appPackageName: String = appPackageName
        override val appNameFallbackResId: Int = appNameFallbackResId

        override fun buildIntent(context: Context, keyword: String): Intent {
            val query = if (keywordEncode) keyword.encodeKeyword() else keyword
            val data = MessageFormat.format(dataPattern, query)
            return Intent(action)
                .addCategory(category)
                .setData(data.toUri())
                .setPackage(if (matchPackage) appPackageName else null)
        }
    }
}

interface OneShot {

    companion object {

        private val cachedAppName = HashMap<String, CharSequence>()

        fun searchGo(context: Context, oneShot: OneShot, keyword: String) {
            if (TextUtils.isEmpty(keyword)) {
                Toast.makeText(context, "请输入搜索内容", Toast.LENGTH_SHORT).show()
                return
            }
            if (oneShot.appPackageName.isNotEmpty() && !context.isAppInstalled(oneShot.appPackageName)) {
                Toast.makeText(
                    context, "尚未安装${oneShot.getAppName(context)}", Toast.LENGTH_SHORT
                ).show()
                return
            }
            try {
                oneShot.onSearchGo(context, keyword)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    val appPackageName: String

    @get:StringRes
    val appNameFallbackResId: Int

    open fun getAppName(context: Context): CharSequence {
        val cachedName = cachedAppName[appPackageName]
        if (cachedName != null) {
            return cachedName
        }
        if (appPackageName.isEmpty()) {
            return context.getString(appNameFallbackResId)
        }

        val packageManager = context.packageManager
        val info = try {
            packageManager.getApplicationInfo(appPackageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return context.getString(appNameFallbackResId)
        }
        return packageManager.getApplicationLabel(info).also {
            cachedAppName[appPackageName] = it
        }
    }

    fun buildIntent(context: Context, keyword: String): Intent

    open fun onSearchGo(context: Context, keyword: String) {
        val intent = buildIntent(context, keyword)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
