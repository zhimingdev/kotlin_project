package com.test.myapplication.ui.activity

import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.test.myapplication.base.BaseActivity
import android.content.Intent
import android.net.Uri
import com.test.myapplication.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun initData() {
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
            try {
                if (url!!.startsWith("http:") || url.startsWith("https:")) {
                    view!!.loadUrl(url)
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                return true
            } catch (e: Exception) {
                return false
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

    private val click  = object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (web.canGoBack()){
                web.goBack()
            }else{
                finish()
            }
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