package com.test.sandev.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.robinhood.ticker.TickerUtils
import com.test.sandev.MyApplication
import com.test.sandev.R
import com.test.sandev.api.Api
import com.test.sandev.base.BaseFragment
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.*
import com.test.sandev.utils.*
import com.zhy.base.fileprovider.FileProvider7
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.util.Base64
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.test.sandev.lock.LockPatternUtils
import com.test.sandev.ui.activity.*
import kotlinx.android.synthetic.main.fragment_find.*

class YueDanFragment : BaseFragment() {

    private var cacheDialog:UsualDialogger? = null
    private var logoutdialog: UsualDialogger? = null
    var weburl: String = UrlConstans.kefu_url
    val network by lazy { NetWork() }
    private var mView: AlertView? = null
    private val REQUEST_CODE_TAKE_PHOTO = 0x110
    private val REQUEST_CODE_TAKE_PHOTO_ALBUM = 0x00
    private var mCurrentPhotoPath: String? = null
    private var mFileUri: Uri? = null
    private var mLockPatternUtils : LockPatternUtils? = null

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
    var pm : String? = null
    var nc : String? = null
    var le : String? = null
    var jf : String? = null

    override fun initView(): View {
        EventBus.getDefault().register(this)
        var view = LayoutInflater.from(context).inflate(R.layout.fragment_mine, null)
        verifyStoragePermissions(activity!!)
        return view
    }


    override fun initSandevDate() {
        showName()
        getPackInfo()
        tv_version.text = "V 1.0.0"
        tv_cache.setCharacterList(TickerUtils.getDefaultListForUSCurrency())
        tv_cache.text = "0 B"
        tv_cache.text = FileUtils.getSize(Utils.getApp().cacheDir)
        inv.setNumber("1")
        mLockPatternUtils = LockPatternUtils(context)
        updataStates()
    }

    private fun updataStates() {
        if (PreferenceCache.getGestureSwitch()) {
            iv_hand_switch.setImageResource(R.mipmap.auto_bidding_off)
            ll_setting_hand.visibility = View.VISIBLE
        } else {
            iv_hand_switch.setImageResource(R.mipmap.auto_bidding_on)
            ll_setting_hand.visibility = View.GONE
        }
    }

    private fun getPackInfo() {
        network.getApi(Api::class.java).getPack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<PackModule>(activity!!) {
                    override fun onSuccess(t: PackModule?) {
                        if (t!!.flag!!) {
                            rl_my_jifen.visibility = View.VISIBLE
                        }
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    fun verifyStoragePermissions(activity: FragmentActivity) {
        try {
            //检测是否有写的权限
            var permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMineData() {
        network.getApi(Api::class.java).getMineUrl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<MineModule>(activity!!) {
                    override fun onSuccess(t: MineModule?) {
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }

    private fun showName() {
        var name = SPUtils.getInstance().getString("logininfo", "")
        if (name.isNotBlank()) {
            tv_uaername.text = name
            ll_login.isEnabled = false
            tv_logout.visibility = View.VISIBLE
            var collectEntityDao = MyApplication.instances.daoSession!!.collectEntityDao
            var mutableList = collectEntityDao.loadAll()
            tv_collect.text = mutableList.size.toString()
            tv_starts.text = (0..50).random().toString()
            var bitmap = SPUtils.getInstance().getString("bitmap")
            if (bitmap.isNotBlank()) {
                var newbyte = Base64.decode(bitmap!!.toByteArray(), 1)
                var stream = BitmapFactory.decodeByteArray(newbyte, 0, newbyte.size)
                iv_head.setImageBitmap(stream)
            } else {
                iv_head.setImageResource(R.mipmap.ic_head)
            }
            getJifen()
        } else {
            tv_uaername.text = "立即登录"
            ll_login.isEnabled = true
            tv_logout.visibility = View.GONE
            tv_collect.text = "0"
            tv_starts.text = "0"
            tv_count.text = "0"
            iv_head.setImageResource(R.mipmap.img_mine)
        }
    }


    private fun getJifen() {
        var id = SPUtils.getInstance().getInt("loginid")
        var map: MutableMap<String, Int> = mutableMapOf()
        map["userId"] = id
        network.getWanApi(Api::class.java).getUserJiFen(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiBaseResponse<MineJifenModule>(activity!!) {
                    override fun onSuccess(t: MineJifenModule?) {
                        tv_count.text = t!!.coinCount.toString()
                        pm = t!!.rank.toString()
                        nc = t!!.username
                        le = t!!.level.toString()
                        jf = t!!.coinCount.toString()
                    }

                    override fun onCodeError(tBaseReponse: BaseResponse<*>) {
                    }

                    override fun onFail(e: ApiError) {
                    }

                })
    }


    override fun initSandevListenter() {
        rl_cache.setOnClickListener {
            cacheDialog =
                    UsualDialogger.Builder(activity)
                            .setTitle("温馨提示")
                            .setMessage("您确定要清除缓存吗？")
                            .setOnConfirmClickListener("确定", object : com.test.sandev.utils.UsualDialogger.onConfirmClickListener {
                                override fun onClick(view: View) {
                                    clearCache()
                                    cacheDialog!!.dismiss()
                                }
                            })
                            .setOnCancelClickListener("取消", object : com.test.sandev.utils.UsualDialogger.onCancelClickListener {
                                override fun onClick(view: View) {
                                    cacheDialog!!.dismiss()
                                }

                            }).build().shown()
        }
        ll_login.setOnClickListener {
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        tv_logout.setOnClickListener {
            logoutdialog =
                    UsualDialogger.Builder(activity)
                            .setTitle("温馨提示")
                            .setMessage("确定要退出登录吗？")
                            .setOnConfirmClickListener("确定") {
                                SPUtils.getInstance().clear()
                                showName()
                                logoutdialog!!.dismiss()
                            }
                            .setOnCancelClickListener("取消") { logoutdialog!!.dismiss() }.build().shown()

        }

        rl_web.setOnClickListener {
            var intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", weburl)
            startActivity(intent)
        }

        rl_my_jifen.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
                var intent = Intent(context, JiFenActivity::class.java)
                intent.putExtra("pm",pm)
                intent.putExtra("nc",nc)
                intent.putExtra("le",le)
                intent.putExtra("jf",jf)
                startActivity(intent)
            } else {
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        ll_collect.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
                var intent = Intent(context, CollectActivity::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        iv_head.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid == 0 || loginid == -1) {
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                show()
            }
        }

        iv_hand_switch.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid != -1) {
                if (PreferenceCache.getGestureSwitch()) {
                    //开的状态
                    PreferenceCache.putGestureSwitch(false)
                    updataStates()
                } else {
                    //关的状态
                    if (mLockPatternUtils!!.savedPatternExists()) {
                        PreferenceCache.putGestureSwitch(true)
                        updataStates()
                    } else {
                        val intent = Intent(context, CreateGesturePasswordActivity::class.java)
                        startActivity(intent)
                    }
                }
            } else {
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        ll_setting_hand.setOnClickListener {
            val intent = Intent(context, UnlockGesturePasswordActivity::class.java)
            startActivity(intent)
        }
        rl_msg.setOnClickListener {
            var loginid = SPUtils.getInstance().getInt("loginid")
            if (loginid == 0 || loginid == -1) {
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(context, MsgActivity::class.java)
                startActivity(intent)
            }
        }

        rl_my_live.setOnClickListener {
            var intent = Intent(context,LifeRecordActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        RxUtil.switchThread(object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?) {
                Glide.get(context).clearDiskCache()
                CleanUtils.cleanInternalCache()
                CleanUtils.cleanExternalCache()
            }
        }, object : RxUtil.OnEventListener {
            override fun onEvent(event: Any?) {
                tv_cache.text = "0 B"
                ToastUtils.showShort("缓存清除成功")
            }
        })
    }

    fun show() {
        mView = AlertView("上传照片", null, "取消", null, arrayOf("拍照", "从相册中选择"), activity, AlertView.Style.ActionSheet, OnItemClickListener { o, position ->
            if (position == 0) {
                takePhotoNoCompress(REQUEST_CODE_TAKE_PHOTO)
                mView!!.dismiss()
            } else if (position == 1) {
                takePhotoAlbum(REQUEST_CODE_TAKE_PHOTO_ALBUM)
                mView!!.dismiss()
            }
        })
        mView!!.show()
    }

    private fun takePhotoNoCompress(code: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            val filename = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(Date()) + ".png"
            val file = File(Environment.getExternalStorageDirectory(), filename)
            mCurrentPhotoPath = file.absolutePath
            println("===地址$mCurrentPhotoPath")
            mFileUri = FileProvider7.getUriForFile(activity, file)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri)
            startActivityForResult(takePictureIntent, code)
        }
    }

    private fun takePhotoAlbum(code: Int) {
        //打开相册
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_TAKE_PHOTO -> {
                    println("tip地址$mCurrentPhotoPath")
                    showCarmerBitmap(mCurrentPhotoPath!!)
                }
                REQUEST_CODE_TAKE_PHOTO_ALBUM -> {
                    println("相册地址${data!!.data}")
                    var fromUri = ImgUpdateDirection.getFilePathFromUri(context, data!!.data)
                    showBitmap(fromUri)
                }
            }
        } else {
            println("出错了")
        }
    }

    //显示相册图片
    private fun showBitmap(uri: String) {
        Luban.with(activity)
                .load(uri)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        println("11")
                    }

                    override fun onSuccess(file: File) {
                        println("====执行了")
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        toRoundBitmap(bitmap!!)
                    }

                    override fun onError(e: Throwable) {
                        // TODO 当压缩过程出现问题时调用
                        println("33$e")
                    }
                }).launch()

    }

    //显示相机图片
    private fun getPath(): String {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath + "/sandev"
        val file = File(path)
        return if (file.mkdirs()) {
            path
        } else path
    }

    private fun showCarmerBitmap(uri: String) {
        Luban.with(activity)
                .load(uri)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        println("11")
                    }

                    override fun onSuccess(file: File) {
                        println("====执行了2222${file.absolutePath}")
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        toRoundBitmap(bitmap!!)
                    }

                    override fun onError(e: Throwable) {
                        // TODO 当压缩过程出现问题时调用
                        println("33$e")
                    }
                }).launch()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(msg: MessageEvent) {
        when (msg.message) {
            "success" -> {
                showName()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        showName()
    }

    override fun onResume() {
        super.onResume()
        updataStates()
    }

    fun toRoundBitmap(bitmap: Bitmap) {
        var width = bitmap.width
        var height = bitmap.height
        var roundPx: Float
        var left: Float
        var top: Float
        var right: Float
        var bottom: Float
        var dst_left: Float
        var dst_top: Float
        var dst_right: Float
        var dst_bottom: Float
        if (width <= height) {
            roundPx = (width / 2).toFloat()
            left = 0f
            top = 0f
            right = width.toFloat()
            bottom = width.toFloat()
            height = width
            dst_left = 0f
            dst_top = 0f
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = (height / 2).toFloat()
            var clip = ((width - height) / 2).toFloat()
            left = clip
            right = width - clip
            top = 0f
            bottom = height.toFloat()
            width = height
            dst_left = 0f
            dst_top = 0f
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }

        var output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(output)

        var paint = Paint()
        var src = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        var dst = Rect(dst_left.toInt(), dst_top.toInt(), dst_right.toInt(), dst_bottom.toInt())
        paint.isAntiAlias = true// 设置画笔无锯齿
        canvas.drawARGB(0, 0, 0, 0) // 填充整个Canvas
        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawCircle(roundPx, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint) //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        var baos = ByteArrayOutputStream()
        //将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        output.compress(Bitmap.CompressFormat.PNG, 80, baos)

        //利用Base64将字节数组输出流中的数据转换成字符串String
        var string = Base64.encodeToString(baos!!.toByteArray(), Base64.DEFAULT)
        SPUtils.getInstance().put("bitmap", string)
        iv_head.setImageBitmap(output)
    }

}