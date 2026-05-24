package com.example.android.themoviesapp.presentation.ui.extensions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

fun View.startRotationAnimation(durationMs: Long = 800L) {
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, 0f, 360f).apply {
        duration = durationMs
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        start()
    }
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) = Unit
        override fun onViewDetachedFromWindow(v: View) {
            animator.cancel()
            removeOnAttachStateChangeListener(this)
        }
    })
}