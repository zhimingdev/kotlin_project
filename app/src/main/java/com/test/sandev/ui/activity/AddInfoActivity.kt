package com.test.sandev.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent
import cn.finalteam.rxgalleryfinal.utils.Logger
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddInfoActivity : BaseActivity() {

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.ACCESS_COARSE_LOCATION")

    override fun getLayoutId(): Int {
        return R.layout.activity_add
    }

    override fun initData() {
        verifyStoragePermissions(this)
    }

    override fun initLisenter() {
        rl_address.setOnClickListener {
            val intent = Intent(this,MapActivity::class.java)
            startActivityForResult(intent,0)
        }

        tv_fb.setOnClickListener {
            ToastUtils.showShort("发表成功")
            finish()
        }
        rl_bg.setOnClickListener {
            openImageSelectMultiMethod(1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == 1) {
            var result = data!!.getStringExtra("result")
            tv_a.text = result
        }
    }

    fun verifyStoragePermissions(activity: FragmentActivity) {
        try {
            //检测是否有写的权限
            var permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.ACCESS_COARSE_LOCATION")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private fun startAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var packageName = this.packageName
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun openImageSelectMultiMethod(type: Int) {
        when (type) {
            1 ->  //使用自定义的参数
                RxGalleryFinalApi
                        .getInstance(this)
                        .setImageRadioResultEvent(object: RxBusResultDisposable<ImageRadioResultEvent>() {
                            override fun onEvent(t: ImageRadioResultEvent?) {
                                println("====${Gson().toJson(t)}")
////                                Picasso.with(this@AddInfoActivity).load(t!!.result[0].originalPath).into(iv_mine_head)
                            }
                        }).open()
        }
    }

}