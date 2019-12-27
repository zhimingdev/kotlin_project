package com.test.sandev.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.lock.LockPatternUtils
import com.test.sandev.lock.LockPatternView
import com.test.sandev.utils.PreferenceCache

class UnlockGesturePasswordActivity : BaseActivity() {

    private var mLockPatternView: LockPatternView? = null
    private var mFailedPatternAttemptsSinceLastTimeout = 0
    private val mCountdownTimer: CountDownTimer? = null
    private var mHeadTextView: TextView? = null
    private var mToast: Toast? = null
    private val phoneNumber = ""
    private var rl_ges: RelativeLayout? = null
    private var mLockPatternUtils: LockPatternUtils? = null
    private var mASwitch: Boolean = false
    private var mFrom: String? = null
    private var forgetText: TextView? = null

    private val mClearPatternRunnable = Runnable { mLockPatternView!!.clearPattern() }

    protected var mChooseNewLockPatternListener: LockPatternView.OnPatternListener = object : LockPatternView.OnPatternListener {

        override fun onPatternStart() {
            mLockPatternView!!.removeCallbacks(mClearPatternRunnable)
            patternInProgress()
        }

        override fun onPatternCleared() {
            mLockPatternView!!.removeCallbacks(mClearPatternRunnable)
        }

        override fun onPatternDetected(pattern: List<LockPatternView.Cell>?) {
            if (pattern == null)
                return
            if (mLockPatternUtils!!.checkPattern(pattern)) {
                if ("SplashActivity" == mFrom) {
                    this@UnlockGesturePasswordActivity.finish()
                    val intent = Intent(this@UnlockGesturePasswordActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    mLockPatternView!!
                            .setDisplayMode(LockPatternView.DisplayMode.Correct)
                    showToast("解锁成功,可重新绘制手势")
                    this@UnlockGesturePasswordActivity.finish()
                    val intent = Intent(
                            this@UnlockGesturePasswordActivity,
                            CreateGesturePasswordActivity::class.java)
                    // 打开新的Activity
                    startActivity(intent)
                }
            } else {
                mLockPatternView!!
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong)
                mFailedPatternAttemptsSinceLastTimeout++
                val retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT - mFailedPatternAttemptsSinceLastTimeout
                if (retry >= 0) {
                    if (retry == 0) {
                        showToast("您已5次输错密码")
                        mLockPatternUtils!!.clearLock()
                        PreferenceCache.putGestureSwitch(false)
                        finish()
                    }
                    mHeadTextView!!.text = "密码错误，还可以再输入" + retry + "次"
                    mHeadTextView!!.setTextColor(Color.RED)
                    postClearPatternRunnable()

                }
            }
        }

        override fun onPatternCellAdded(pattern: List<LockPatternView.Cell>) {

        }

        private fun patternInProgress() {}
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_gesturepassword_unlock
    }

    override fun initData() {
        mFrom = intent.getStringExtra("from")
        rl_ges = findViewById<View>(R.id.rl_more_ges) as RelativeLayout
        mLockPatternView = this
                .findViewById<View>(R.id.gesturepwd_unlock_lockview) as LockPatternView
        mHeadTextView = findViewById<View>(R.id.gesturepwd_unlock_text) as TextView
        forgetText = findViewById<View>(R.id.gesturepwd_unlock_forget) as TextView
        mLockPatternView!!.setOnPatternListener(mChooseNewLockPatternListener)
        mLockPatternView!!.isTactileFeedbackEnabled = true
        mLockPatternUtils = LockPatternUtils(this)
        mHeadViewInit()
        mASwitch = PreferenceCache.getGestureSwitch()
    }

    override fun initLisenter() {
        forgetText!!.setOnClickListener {
            if ("SplashActivity" == mFrom) {
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            mLockPatternUtils!!.clearLock()
            PreferenceCache.putGestureSwitch(false)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //backSettingActivity();
            PreferenceCache.putGestureSwitch(mASwitch)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun mHeadViewInit() {
        if (TextUtils.isEmpty(phoneNumber)) {
            mHeadTextView!!.text = "请绘制解锁密码"
            mHeadTextView!!.setTextColor(Color.parseColor("#ff6600"))
        } else {
            mHeadTextView!!.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun postClearPatternRunnable() {
        mLockPatternView!!.removeCallbacks(mClearPatternRunnable)
        mLockPatternView!!.postDelayed(mClearPatternRunnable, 2000)
    }


    private fun showToast(message: CharSequence) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
        } else {
            mToast!!.setText(message)
        }
        mToast!!.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        mCountdownTimer?.cancel()
    }
}
