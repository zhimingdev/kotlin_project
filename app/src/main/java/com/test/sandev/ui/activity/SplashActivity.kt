package com.test.sandev.ui.activity

import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.lock.LockPatternUtils
import com.test.sandev.utils.PreferenceCache
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), ViewPropertyAnimatorListener {
    override fun onAnimationEnd(view: View?) {
        var mGestureSwitch = PreferenceCache.getGestureSwitch()
        var mLockPatternUtils = LockPatternUtils(this)
        var mLock = mLockPatternUtils.savedPatternExists()
        if (mLock && mGestureSwitch) {
            var intent = Intent(this,UnlockGesturePasswordActivity::class.java)
            intent.putExtra("from","SplashActivity")
            startActivity(intent)
        }else {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    override fun onAnimationStart(view: View?) {
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        ViewCompat.animate(imageView).setDuration(2000).scaleX(1.0f).scaleY(1.0f).setListener(this).start()
    }
}