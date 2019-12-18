package com.test.sandev.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import com.test.sandev.R
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import kotlinx.android.synthetic.main.fragment_kefu.*

class KeFuFragment : BaseFragment() {
    override fun initView(): View {
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_kefu,null)
        return view
    }

    override fun initDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptThirdPartyCookies(webview, true)
        }
        rl_kefu_back.visibility = View.GONE
        var websetting = webview.settings
        websetting.javaScriptEnabled = true
        websetting.javaScriptCanOpenWindowsAutomatically = true
        websetting.allowFileAccess = true
        websetting.displayZoomControls = true
        websetting.setSupportZoom(true)
        websetting.cacheMode = WebSettings.LOAD_NO_CACHE
        websetting.domStorageEnabled = true
        websetting.databaseEnabled = true
        webview.webViewClient = webviewclient
        webview.webChromeClient = webChromeClient
        webview.loadUrl(UrlConstans.kefu_url)
    }

    private var webviewclient = object : WebViewClient(){
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
            webview.visibility = View.VISIBLE
            ll_web_load.visibility = View.GONE
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun getDefaultVideoPoster(): Bitmap? {
            return try{
                BitmapFactory.decodeResource(activity!!.resources,
                        R.mipmap.ic_launcher)
            }catch(e : Exception ){
                super.getDefaultVideoPoster()
            }
        }
    }
}