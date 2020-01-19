package com.test.sandev.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.webkit.*
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.new_web.*


class NewWeb : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.new_web
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptThirdPartyCookies(new_web, true)
        }
        var weburl :String = intent.getStringExtra("url")
        var websetting = new_web.settings
        websetting.javaScriptEnabled = true
        websetting.javaScriptCanOpenWindowsAutomatically = true
        websetting.allowFileAccess = true
        websetting.displayZoomControls = true
        websetting.setSupportZoom(true)
        websetting.cacheMode = WebSettings.LOAD_NO_CACHE
        websetting.domStorageEnabled = true
        websetting.databaseEnabled = true
        new_web.webViewClient = webviewclient
        new_web.webChromeClient = webChromeClient
        new_web.loadUrl(weburl)
    }

    private var webviewclient = object : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return try {
                if (url!!.startsWith("http:") || url.startsWith("https:")) {
                    view!!.loadUrl(url)
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun getDefaultVideoPoster(): Bitmap? {
            return try{
                BitmapFactory.decodeResource(applicationContext.resources,
                        R.mipmap.ic_launcher)
            }catch(e : Exception ){
                super.getDefaultVideoPoster()
            }
        }
    }
}