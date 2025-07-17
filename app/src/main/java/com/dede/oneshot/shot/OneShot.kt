package com.dede.oneshot.shot

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.DrawableRes
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
    buildOneShot(
        appPackageName = "com.sina.weibo",
        appNameFallbackResId = R.string.app_name_weibo,
        dataPattern = "sinaweibo://searchall?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.ruguoapp.jike",
        appNameFallbackResId = R.string.app_name_jike,
        dataPattern = "jike://page.jk/search?type=integrated&keywords={0}",
    ),
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

    // 资讯
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
    buildOneShot(
        appPackageName = "com.tencent.news",
        appNameFallbackResId = R.string.app_name_tencent_news,
        dataPattern = "qqnews://article_9527?chlid=news_search&search_keyword={0}",
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
    buildOneShot(
        appPackageName = "com.qiyi.video",
        appNameFallbackResId = R.string.app_name_iqiyi  ,
        dataPattern = "iqiyi://mobile/search?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.youku.phone",
        appNameFallbackResId = R.string.app_name_youku  ,
        dataPattern = "youku://soku/searchresult?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.tencent.qqlive",
        appNameFallbackResId = R.string.app_name_qqlive  ,
        dataPattern = "txvideo://v.qq.com/SearchPagerActivity?searchKey={0}&autoSearch=1",
    ),

    // 音乐
    buildOneShot(
        appPackageName = "com.netease.cloudmusic",
        appNameFallbackResId = R.string.app_name_netease,
        dataPattern = "orpheus://nm/search/result?keyword={0}",
    ),
    buildOneShot(
        appPackageName = "com.tencent.qqmusic",
        appNameFallbackResId = R.string.app_name_qqmusic,
        // {"query":"{query}"}
        dataPattern = "qqmusic://qq.com/ui/search?p=%7B%22query%22%3A%22{0}%22%7D",
    ),

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
        appIconFallbackResId = R.drawable.logo_bing,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.bing.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_baidu,
        appIconFallbackResId = R.drawable.logo_baidu,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.baidu.com/s?wd={0}",
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_google,
        appIconFallbackResId = R.drawable.logo_google,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "https://www.google.com/search?q={0}",
    ),
    buildOneShot(
        appPackageName = "com.android.browser",
        appNameFallbackResId = R.string.app_name_browser,
        appIconFallbackResId = R.drawable.rounded_language_24,
        action = Intent.ACTION_WEB_SEARCH,
        category = Intent.CATEGORY_BROWSABLE,
        buildIntent = { keyword ->
            putExtra(SearchManager.QUERY, keyword)
        }
    ),
    buildOneShot(
        appPackageName = "",
        appNameFallbackResId = R.string.app_name_market,
        appIconFallbackResId = R.drawable.rounded_android_24,
        category = Intent.CATEGORY_BROWSABLE,
        dataPattern = "market://search?q={0}",
    ),
)

fun String.encodeKeyword(): String {
    return Uri.encode(this)
}

fun buildOneShot(
    appPackageName: String,
    @StringRes appNameFallbackResId: Int,
    @DrawableRes appIconFallbackResId: Int = R.drawable.rounded_search_off_24,
    action: String = Intent.ACTION_VIEW,
    category: String = Intent.CATEGORY_DEFAULT,
    dataPattern: String? = null,// scheme://host/path?search={0}
    keywordEncode: Boolean = true,
    matchPackage: Boolean = false,
    buildIntent: Intent.(keyword: String) -> Unit = {}
): OneShot {
    return object : OneShot {
        override val appPackageName: String = appPackageName
        override val appNameFallbackResId: Int = appNameFallbackResId
        override val appIconFallbackResId: Int = appIconFallbackResId

        override fun buildIntent(context: Context, keyword: String): Intent {
            return Intent(action)
                .addCategory(category)
                .apply {
                    if (matchPackage) {
                        setPackage(appPackageName)
                    }
                    if (dataPattern != null) {
                        val query = if (keywordEncode) keyword.encodeKeyword() else keyword
                        val data = MessageFormat.format(dataPattern, query)
                        setData(data.toUri())
                    }
                    buildIntent(keyword)
                }
        }
    }
}

interface OneShot {

    companion object {

        fun searchGo(context: Context, oneShot: OneShot, keyword: String) {
            if (TextUtils.isEmpty(keyword)) {
                Toast.makeText(context, "请输入搜索内容", Toast.LENGTH_SHORT).show()
                return
            }
            if (oneShot.appPackageName.isNotEmpty() &&
                !OneShotAppsManager.get().isAppInstalled(oneShot.appPackageName)
            ) {
                Toast.makeText(
                    context, "尚未安装${oneShot.getAppName()}", Toast.LENGTH_SHORT
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

    @get:DrawableRes
    val appIconFallbackResId: Int

    fun getAppIcon(): Drawable? {
        return OneShotAppsManager.get().getAppIcon(appPackageName, appIconFallbackResId)
    }

    fun getAppName(): CharSequence {
        return OneShotAppsManager.get().getAppName(appPackageName, appNameFallbackResId)
    }

    fun buildIntent(context: Context, keyword: String): Intent

    open fun onSearchGo(context: Context, keyword: String) {
        val intent = buildIntent(context, keyword)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
