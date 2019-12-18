package com.test.sandev.ui.activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess
import android.widget.Toast
import android.view.KeyEvent
import com.test.sandev.base.BaseActivity
import com.test.sandev.ui.fragment.VBangFragment
import com.test.sandev.utils.FragmentUtils
import com.xiao.nicevideoplayer.NiceVideoPlayerManager


class MainActivity : BaseActivity(){
    //需要显示的fragment
    var showfragment : Fragment? = null
    //当前显示的fragment
    var currentfragment :Fragment? = null
    var lasttime : Long = 0

    override fun getLayoutId(): Int {
        return com.test.sandev.R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initLisenter() {
        bottomBar.setOnTabSelectListener{
            var transaction = supportFragmentManager.beginTransaction()
             showfragment = FragmentUtils.instance.getFragment(it)
            if (currentfragment != null && showfragment != currentfragment) {
                transaction.hide(currentfragment!!)
            }
            if (showfragment!!.isAdded) {
                transaction.show(FragmentUtils.instance.getFragment(it))
            }else{
                transaction.add(com.test.sandev.R.id.container,showfragment!!)
            }
            transaction.commit()
            currentfragment = showfragment
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val secondTime = System.currentTimeMillis()
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentfragment is VBangFragment) {
                if (NiceVideoPlayerManager.instance().onBackPressd()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
                    return true
                }else {
                    if (secondTime - lasttime < 2000) {
                        exitProcess(0)
                    } else {
                        ToastUtils.showShort("再按一次返回键退出")
                        lasttime = System.currentTimeMillis()
                    }
                }
            }else {
                if (secondTime - lasttime < 2000) {
                    exitProcess(0)
                } else {
                    ToastUtils.showShort("再按一次返回键退出")
                    lasttime = System.currentTimeMillis()
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
