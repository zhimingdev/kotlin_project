package com.test.sandev.ui.activity

import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.View
import com.test.sandev.base.BaseActivity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import com.test.sandev.R
import com.xiao.nicevideoplayer.LogUtil
import kotlinx.android.synthetic.main.activity_web.*
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION.SDK_INT
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.os.Build
import android.webkit.*


class WebActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun initData() {
        if (SDK_INT >= LOLLIPOP) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptThirdPartyCookies(web, true)
        }

        var weburl :String = intent.getStringExtra("url")
        var websetting = web.settings
        websetting.javaScriptEnabled = true
        websetting.javaScriptCanOpenWindowsAutomatically = true
        websetting.allowFileAccess = true
        websetting.displayZoomControls = true
        websetting.setSupportZoom(true)
        websetting.cacheMode = WebSettings.LOAD_NO_CACHE
        websetting.domStorageEnabled = true
        websetting.databaseEnabled = true
        web.webViewClient = webviewclient
        web.webChromeClient = webChromeClient
        web!!.setOnKeyListener(OnKeyEvent)
        rl_back.setOnClickListener(click)
        web.loadUrl(weburl)
    }

    private var webviewclient = object :WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return try {
                if (url!!.startsWith("http:") || url.startsWith("https:")) {
                    view!!.loadUrl(url)
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                true
            } catch (e: Exception) {
                false
            }

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            web.visibility = View.VISIBLE
            ll_load.visibility = View.GONE
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            if (title == null || title ==="") {
                ll_title.visibility = View.GONE
            }else{
                ll_title.visibility = View.VISIBLE
                tv_title.text = title
            }

        }

        override fun getDefaultVideoPoster(): Bitmap? {
            return try{
                BitmapFactory.decodeResource(applicationContext.resources,
                        R.mipmap.ic_launcher)
            }catch(e : Exception ){
                super.getDefaultVideoPoster()
            }
        }
    }

    private val OnKeyEvent = View.OnKeyListener { v, keyCode, event ->
        val action = event.action
        val webView = v as WebView
        if (KeyEvent.ACTION_DOWN == action && KeyEvent.KEYCODE_BACK == keyCode) {
            if (webView?.canGoBack()) {
                webView.goBack()
                return@OnKeyListener true
            }else {
                finish()
            }
        }
        false
    }

    private val click  = View.OnClickListener {
        if (web.canGoBack()){
            web.goBack()
        }else{
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        web?.clearCache(true)
        web?.stopLoading()
        web?.webViewClient = null
        web?.webChromeClient = null
        web?.removeAllViews()
        web == null
    }

}