package com.test.sandev.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.lock.LockPatternUtils
import com.test.sandev.lock.LockPatternView
import com.test.sandev.utils.PreferenceCache

import java.util.ArrayList

class CreateGesturePasswordActivity : BaseActivity() {
    private var mLockPatternView: LockPatternView? = null
    var mHeaderText: TextView? = null
    private var vFull: View? = null
    protected var mChosenPattern: List<LockPatternView.Cell>? = null
    private var mToast: Toast? = null
    private var mUiStage = Stage.Introduction
    private var mPreviewViews = Array<Array<View?>>(3) { arrayOfNulls(3) }
    private val flag = ""
    /**
     * The patten used during the help screen to show how to draw a pattern.
     */
    private val mAnimatePattern = ArrayList<LockPatternView.Cell>()
    private var mLockPatternUtils: LockPatternUtils? = null
    private var mASwitch: Boolean = false

    internal var statusHeight: Int = 0

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
            // Log.i("way", "result = " + pattern.toString());
            if (mUiStage == Stage.NeedToConfirm || mUiStage == Stage.ConfirmWrong) {
                checkNotNull(mChosenPattern) { "null chosen pattern in stage 'need to confirm" }
                if (mChosenPattern == pattern) {
                    updateStage(Stage.ChoiceConfirmed)
                } else {
                    updateStage(Stage.ConfirmWrong)
                }
            } else if (mUiStage == Stage.Introduction || mUiStage == Stage.ChoiceTooShort) {
                if (pattern.size < LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
                    updateStage(Stage.ChoiceTooShort)
                } else {
                    mChosenPattern = ArrayList(
                            pattern)
                    updateStage(Stage.FirstChoiceValid)
                }
            } else {
                throw IllegalStateException("Unexpected stage " + mUiStage
                        + " when " + "entering the pattern.")
            }
        }

        override fun onPatternCellAdded(pattern: List<LockPatternView.Cell>) {

        }

        private fun patternInProgress() {
            mHeaderText!!.setText(R.string.lockpattern_recording_inprogress)
        }
    }

    private val common_layout: RelativeLayout? = null

    /**
     * The states of the left footer button.
     */
    internal enum class LeftButtonMode
    /**
     * @param text
     * The displayed text for this mode.
     * @param enabled
     * Whether the button should be enabled.
     */
    private constructor(val text: Int, val enabled: Boolean) {
        Cancel(android.R.string.cancel, true), CancelDisabled(
                android.R.string.cancel, false),
        Retry(
                R.string.lockpattern_retry_button_text, true),
        RetryDisabled(
                R.string.lockpattern_retry_button_text, false),
        Gone(
                ID_EMPTY_MESSAGE, false)
    }

    /**
     * The states of the right button.
     */
    internal enum class RightButtonMode
    /**
     * @param text
     * The displayed text for this mode.
     * @param enabled
     * Whether the button should be enabled.
     */
    private constructor(val text: Int, val enabled: Boolean) {
        Continue(R.string.lockpattern_continue_button_text, true),
        ContinueDisabled(
                R.string.lockpattern_continue_button_text, false),
        Confirm(
                R.string.lockpattern_confirm_button_text, true),
        ConfirmDisabled(
                R.string.lockpattern_confirm_button_text, false),
        Ok(
                android.R.string.ok, true)
    }

    /**
     * Keep track internally of where the user is in choosing a pattern.
     */
    protected enum class Stage
    /**
     * @param headerMessage
     * The message displayed at the top.
     * @param leftMode
     * The mode of the left button.
     * @param rightMode
     * The mode of the right button.
     * @param footerMessage
     * The footer message.
     * @param patternEnabled
     * Whether the pattern widget is enabled.
     */
    private constructor(internal val headerMessage: Int, internal val leftMode: LeftButtonMode,
                        internal val rightMode: RightButtonMode, internal val footerMessage: Int,
                        internal val patternEnabled: Boolean) {

        Introduction(R.string.lockpattern_recording_intro_header,
                LeftButtonMode.Cancel, RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        HelpScreen(
                R.string.lockpattern_settings_help_how_to_record,
                LeftButtonMode.Gone, RightButtonMode.Ok, ID_EMPTY_MESSAGE,
                false),
        ChoiceTooShort(
                R.string.lockpattern_recording_incorrect_too_short,
                LeftButtonMode.Retry, RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        FirstChoiceValid(
                R.string.lockpattern_pattern_entered_header,
                LeftButtonMode.Retry, RightButtonMode.Continue,
                ID_EMPTY_MESSAGE, false),
        NeedToConfirm(
                R.string.lockpattern_need_to_confirm, LeftButtonMode.Cancel,
                RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true),
        ConfirmWrong(
                R.string.lockpattern_need_to_unlock_wrong,
                LeftButtonMode.Cancel, RightButtonMode.ConfirmDisabled,
                ID_EMPTY_MESSAGE, true),
        ChoiceConfirmed(
                R.string.lockpattern_pattern_confirmed_header,
                LeftButtonMode.Cancel, RightButtonMode.Confirm,
                ID_EMPTY_MESSAGE, false)
    }

    private fun showToast(message: CharSequence) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(message)
        }

        mToast!!.show()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_gesturepassword_create
    }

    override fun initData() {
        mAnimatePattern.add(LockPatternView.Cell.of(0, 0))
        mAnimatePattern.add(LockPatternView.Cell.of(0, 1))
        mAnimatePattern.add(LockPatternView.Cell.of(1, 1))
        mAnimatePattern.add(LockPatternView.Cell.of(2, 1))
        mAnimatePattern.add(LockPatternView.Cell.of(2, 2))
        mLockPatternUtils = LockPatternUtils(this)
        mLockPatternView = this
                .findViewById<View>(R.id.gesturepwd_create_lockview) as LockPatternView
        mHeaderText = findViewById<View>(R.id.gesturepwd_create_text) as TextView
        vFull = findViewById(R.id.v_full)
        mLockPatternView!!.setOnPatternListener(mChooseNewLockPatternListener)
        mLockPatternView!!.isTactileFeedbackEnabled = true

        initPreviewViews()
        initStatusHeight()
        updateStage(Stage.Introduction)
        mASwitch = PreferenceCache.getGestureSwitch()
    }

    private fun initStatusHeight() {
        val statusHeight = getStatusBarHeights(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mlp = vFull!!.layoutParams as ViewGroup.MarginLayoutParams
            mlp.height += statusHeight / 2
            vFull!!.layoutParams = mlp
        }
    }

    private fun initPreviewViews() {
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0)
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1)
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2)
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3)
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4)
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5)
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6)
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7)
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8)
    }

    private fun updatePreviewViews() {
        if (mChosenPattern == null)
            return
        Log.i("way", "result = " + mChosenPattern!!.toString())
        for (cell in mChosenPattern!!) {
            Log.i("way", "cell.getRow() = " + cell.row
                    + ", cell.getColumn() = " + cell.column)
            mPreviewViews[cell.row][cell.column]!!.setBackgroundResource(R.mipmap.gesture_create_grid_selected)

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal)
        if (mChosenPattern != null) {
            outState.putString(KEY_PATTERN_CHOICE,
                    LockPatternUtils.patternToString(mChosenPattern))
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (mUiStage == Stage.HelpScreen) {
                updateStage(Stage.Introduction)
                return true
            }
            backSettingActivity()
            finish()
        }
        if (keyCode == KeyEvent.KEYCODE_MENU && mUiStage == Stage.Introduction) {
            updateStage(Stage.HelpScreen)
            return true
        }
        return false
    }

    private fun updateStage(stage: Stage) {
        mUiStage = stage
        if (stage == Stage.ChoiceTooShort) {
            mHeaderText!!.text = resources.getString(stage.headerMessage,
                    LockPatternUtils.MIN_LOCK_PATTERN_SIZE)
        } else {
            mHeaderText!!.setText(stage.headerMessage)
        }

        if (stage.patternEnabled) {
            mLockPatternView!!.enableInput()
        } else {
            mLockPatternView!!.disableInput()
        }

        mLockPatternView!!.setDisplayMode(LockPatternView.DisplayMode.Correct)

        when (mUiStage) {
            CreateGesturePasswordActivity.Stage.Introduction -> mLockPatternView!!.clearPattern()
            CreateGesturePasswordActivity.Stage.HelpScreen -> mLockPatternView!!.setPattern(LockPatternView.DisplayMode.Animate, mAnimatePattern)
            CreateGesturePasswordActivity.Stage.ChoiceTooShort -> {
                mLockPatternView!!.setDisplayMode(LockPatternView.DisplayMode.Wrong)
                postClearPatternRunnable()
            }
            CreateGesturePasswordActivity.Stage.FirstChoiceValid -> if (mUiStage.rightMode == RightButtonMode.Continue) {
                check(mUiStage == Stage.FirstChoiceValid) {
                    ("expected ui stage "
                            + Stage.FirstChoiceValid + " when button is "
                            + RightButtonMode.Continue)
                }
                updateStage(Stage.NeedToConfirm)
            } else if (mUiStage.rightMode == RightButtonMode.Confirm) {
                check(mUiStage == Stage.ChoiceConfirmed) {
                    ("expected ui stage "
                            + Stage.ChoiceConfirmed + " when button is "
                            + RightButtonMode.Confirm)
                }
                saveChosenPatternAndFinish()
            } else if (mUiStage.rightMode == RightButtonMode.Ok) {
                check(mUiStage == Stage.HelpScreen) {
                    ("Help screen is only mode with ok button, but "
                            + "stage is " + mUiStage)
                }
                mLockPatternView!!.clearPattern()
                mLockPatternView!!.setDisplayMode(LockPatternView.DisplayMode.Correct)
                updateStage(Stage.Introduction)
            }
            CreateGesturePasswordActivity.Stage.NeedToConfirm -> {
                mLockPatternView!!.clearPattern()
                updatePreviewViews()
            }
            CreateGesturePasswordActivity.Stage.ConfirmWrong -> {
                mLockPatternView!!.setDisplayMode(LockPatternView.DisplayMode.Wrong)
                postClearPatternRunnable()
            }
            CreateGesturePasswordActivity.Stage.ChoiceConfirmed -> if (mUiStage.rightMode == RightButtonMode.Continue) {
                check(mUiStage == Stage.FirstChoiceValid) {
                    ("expected ui stage "
                            + Stage.FirstChoiceValid + " when button is "
                            + RightButtonMode.Continue)
                }
                updateStage(Stage.NeedToConfirm)
            } else if (mUiStage.rightMode == RightButtonMode.Confirm) {
                check(mUiStage == Stage.ChoiceConfirmed) {
                    ("expected ui stage "
                            + Stage.ChoiceConfirmed + " when button is "
                            + RightButtonMode.Confirm)
                }
                saveChosenPatternAndFinish()
            } else if (mUiStage.rightMode == RightButtonMode.Ok) {
                check(mUiStage == Stage.HelpScreen) {
                    ("Help screen is only mode with ok button, but "
                            + "stage is " + mUiStage)
                }
                mLockPatternView!!.clearPattern()
                mLockPatternView!!.setDisplayMode(LockPatternView.DisplayMode.Correct)
                updateStage(Stage.Introduction)
            }
        }

    }

    private fun postClearPatternRunnable() {
        mLockPatternView!!.removeCallbacks(mClearPatternRunnable)
        mLockPatternView!!.postDelayed(mClearPatternRunnable, 2000)
    }

    private fun backSettingActivity() {
        PreferenceCache.putGestureSwitch(mASwitch)
        finish()
    }

    private fun saveChosenPatternAndFinish() {
        val sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE) //私有数据
        val editor = sharedPreferences.edit()
        val s1 = LockPatternUtils.patternToString(mChosenPattern)
        val key = sharedPreferences.getString("1", "")
        println("加密手势是$s1-----------$key")
        if (key == s1) {
            showToast("不能与原手势密码相同")
            val intent = Intent(this, CreateGesturePasswordActivity::class.java)
            // 打开新的Activity
            startActivity(intent)
            this.finish()
        } else {
            mLockPatternUtils!!.saveLockPattern(mChosenPattern)
            showToast("手势密码设置成功")
            val s = LockPatternUtils.patternToString(mChosenPattern)
            editor.putString("1", s)
            editor.commit()//提交修改
            PreferenceCache.putGestureFlag(true)
            PreferenceCache.putGestureSwitch(true)
            finish()
        }

    }

    companion object {

        private val ID_EMPTY_MESSAGE = -1
        private val KEY_UI_STAGE = "uiStage"
        private val KEY_PATTERN_CHOICE = "chosenPattern"

        fun getStatusBarHeights(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier(
                    "status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}
