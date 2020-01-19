package com.test.sandev.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.maning.updatelibrary.InstallUtils
import com.maning.updatelibrary.InstallUtils.InstallCallBack
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.constans.UrlConstans
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : BaseActivity() {

    private var downloadpath :String? = null
    private var downloadUrl :String? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE")

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

    override fun initLisenter() {
        web.setDownloadListener { url, _, _, _, _ ->
            if (SDK_INT > 23) {
                downloadUrl = url
                verifyStoragePermissions(this)
            }else {
                ToastUtils.showShort("下载中,请耐心等待")
                loadApk(url)
            }
        }
        iv_like.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
            } else {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        iv_web_msg.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
            } else {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        iv_fen.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
            } else {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadApk(url: String?) {
        //下载APK
        InstallUtils.with(this)
                .setApkUrl(url)
                //非必须-下载保存的文件的完整路径+/name.apk，使用自定义路径需要获取读写权限
                .setApkPath(UrlConstans.APK_SAVE_PATH)
                .setCallBack(object : InstallUtils.DownloadCallBack{
                    override fun onComplete(path: String?) {
                        downloadpath = path
                        InstallUtils.checkInstallPermission(this@WebActivity,object : InstallUtils.InstallPermissionCallBack{
                            override fun onGranted() {
                                //安装APK
                                InstallUtils.installAPK(this@WebActivity, downloadpath, object : InstallCallBack {
                                    override fun onSuccess() { //onSuccess：表示系统的安装界面被打开
                                        //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                                        Toast.makeText(this@WebActivity, "正在安装程序,请勿取消", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onFail(e: java.lang.Exception) { //安装出现异常，这里可以提示用用去用浏览器下载安装
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadpath))
                                        startActivity(intent)
                                        finish()
                                    }
                                })
                            }

                            override fun onDenied() {
                                //弹出弹框提醒用户
                                var alertDialog = AlertDialog.Builder(this@WebActivity)
                                        .setTitle("温馨提示")
                                        .setMessage("必须授权才能安装APK，请设置允许安装")
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("设置") { _, _ ->
                                            //打开设置页面
                                            InstallUtils.openInstallPermissionSetting(this@WebActivity, object : InstallUtils.InstallPermissionCallBack {
                                                override fun onGranted() {
                                                    //安装APK
                                                    InstallUtils.installAPK(this@WebActivity, downloadpath, object : InstallCallBack {
                                                        override fun onSuccess() { //onSuccess：表示系统的安装界面被打开
                                                            //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                                                            Toast.makeText(this@WebActivity, "正在安装程序,请勿取消", Toast.LENGTH_SHORT).show()
                                                        }

                                                        override fun onFail(e: java.lang.Exception) { //安装出现异常，这里可以提示用用去用浏览器下载安装
                                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadpath))
                                                            startActivity(intent)
                                                            finish()
                                                        }
                                                    })
                                                }

                                                override fun onDenied() {
                                                    //还是不允许咋搞？
                                                    finish()
                                                }
                                            })
                                        }
                                        .create()
                                alertDialog.show()
                            }

                        })
                    }

                    override fun onFail(p0: java.lang.Exception?) {
                        println("错误====${p0.toString()}")
                    }

                    override fun onLoading(p0: Long, p1: Long) {
                    }

                    override fun onStart() {
                    }

                    override fun cancle() {
                    }
                })
                .startDownload()
    }

    private var webviewclient = object :WebViewClient(){
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

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            web.visibility = View.VISIBLE
            ll_load.visibility = View.GONE
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

    private val OnKeyEvent = View.OnKeyListener { v, keyCode, event ->
        val action = event.action
        if (KeyEvent.ACTION_DOWN == action && KeyEvent.KEYCODE_BACK == keyCode) {
            finish()
        }
        false
    }

    private val click  = View.OnClickListener {
        finish()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showShort("下载中,请耐心等待")
            loadApk(downloadUrl)
        }else{
           startAppSettings()
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private fun startAppSettings() {
        try {
            val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            )
            var packageName = this.packageName
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun verifyStoragePermissions(activity: FragmentActivity) {
        try {
            //检测是否有写的权限
            var permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}