package com.test.myapplication.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator

import com.test.myapplication.R
import com.test.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation()
        }
    }

    override fun initLisenter() {
        fab.setOnClickListener {
            animateRevealClose()
        }
    }

    private fun ShowEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition)
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                cv_add.visibility = View.GONE
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }


        })
    }

    fun animateRevealShow() {
        val mAnimator =
            ViewAnimationUtils.createCircularReveal(cv_add, cv_add.getWidth() / 2, 0, (fab.width / 2).toFloat(), (cv_add.height).toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                cv_add.visibility = View.VISIBLE
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    private fun animateRevealClose() {
        val mAnimator =
            ViewAnimationUtils.createCircularReveal(cv_add, cv_add.width / 2, 0, cv_add.height.toFloat(), (fab.width / 2).toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                cv_add.visibility = View.INVISIBLE
                super.onAnimationEnd(animation)
                fab.setImageResource(R.drawable.plus)
                super@RegisterActivity.onBackPressed()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    override fun onBackPressed() {
        animateRevealClose()
    }
}
