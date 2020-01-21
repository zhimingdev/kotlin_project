package com.test.sandev.ui.activity

import android.annotation.TargetApi
import android.app.Activity
import android.hardware.display.DisplayManager
import android.os.Build
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.test.sandev.R
import com.test.sandev.adapter.VideoDetailAdapter
import com.test.sandev.api.Api
import com.test.sandev.base.BaseActivity
import com.test.sandev.constans.UrlConstans
import com.test.sandev.module.HomeNewBean
import com.test.sandev.utils.*
import com.test.sandev.view.VideoListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*

class VideoDetailActivity : BaseActivity() {


    private var isTransition: Boolean = false

    private var transition: Transition? = null
    private var mMaterialHeader: MaterialHeader? = null
    private val mAdapter by lazy { VideoDetailAdapter(this, itemList) }
    private var itemList = ArrayList<HomeNewBean.Issue.Item>()

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss"); }
    /**
     * Item 详细数据
     */
    var itemData: HomeNewBean.Issue.Item? = null
    private var orientationUtils: OrientationUtils? = null
    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }
    val network by lazy { NetWork() }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun initData() {
        itemData = intent.getSerializableExtra(UrlConstans.BUNDLE_VIDEO_DATA) as HomeNewBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)
        //过渡动画
        initTransition()
        initVideoViewConfig()
        saveWatchVideoHistoryInfo(itemData!!)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        //设置相关视频 Item 的点击事件
        mAdapter.setOnItemDetailClick {
            loadVideoInfo(it)
        }

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, mVideoView)

        /***  下拉刷新  ***/
        //内容跟随偏移
        mRefreshLayout.setEnableLoadMore(false)
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            loadVideoInfo(itemData!!)
            it.finishRefresh()
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo(itemData!!)
        }
    }

    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
                .load(itemData!!.data?.cover?.feed)
                .centerCrop()
                .into(imageView)
        mVideoView.thumbImageView = imageView

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                ToastUtils.showShort("播放失败")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }

        })
    }

    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(watchItem: HomeNewBean.Issue.Item) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        val historyMap = WatchHistoryUtils.getAll(UrlConstans.FILE_WATCH_HISTORY_NAME,this) as Map<*, *>
        for ((key, _) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(UrlConstans.FILE_WATCH_HISTORY_NAME,this, key as String)) {
                WatchHistoryUtils.remove(UrlConstans.FILE_WATCH_HISTORY_NAME,this, key)
            }
        }
        WatchHistoryUtils.putObject(UrlConstans.FILE_WATCH_HISTORY_NAME,this, watchItem,"" + mFormat.format(Date()))
    }

    /**
     * 1.加载视频信息
     */
    fun loadVideoInfo(itemInfo: HomeNewBean.Issue.Item) {
        val playInfo = itemData!!.data?.playInfo
        val netType = NetworkUtil.isWifi(this)
        if (playInfo!!.size > 1) {
            // 当前网络是 Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        setVideo(playUrl)
                        break
                    }
                }
            }
        } else {
            setVideo(itemData!!.data!!.playUrl)
        }
        //设置背景
        val backgroundUrl = itemData!!.data!!.cover.blurred + "/thumbnail/${ScreenUtils.getScreenHeight()!! -  ConvertUtils.dp2px(250f)!!}x${ScreenUtils.getScreenWidth()}"
        backgroundUrl.let {setBackground(it) }
        setVideoInfo(itemData!!)
    }

    private fun setVideo(playUrl: String) {
        mVideoView.setUp(playUrl, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }

    /**
     * 设置视频信息
     */
    fun setVideoInfo(itemInfo: HomeNewBean.Issue.Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        requestRelatedVideo(itemInfo.data?.id?:0)
    }

    private fun requestRelatedVideo(id: Long) {
        network.getBaseApi(Api::class.java).getRelatedData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeNewBean.Issue> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(issue: HomeNewBean.Issue) {
                        setRecentRelatedVideo(issue.itemList)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    private fun setRecentRelatedVideo(itemList: ArrayList<HomeNewBean.Issue.Item>) {
        mAdapter.addData(itemList)
        this.itemList = itemList
    }

    /**
     * 设置背景颜色
     */
    fun setBackground(url: String) {
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(mVideoBackground)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                loadVideoInfo(itemData!!)
                transition?.removeListener(this)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

    override fun initLisenter() {

    }

}