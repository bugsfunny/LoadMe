package com.goodayedi.loadme

import android.animation.ValueAnimator
import android.animation.ValueAnimator.*
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.goodayedi.loadme.util.ButtonState
import kotlin.properties.Delegates

class DownloadButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var buttonTextView: TextView
    private var progressBar: View
    private var animator = ValueAnimator()

    init {
        isClickable = true
        val root = LayoutInflater.from(context).inflate(R.layout.progress_button, this, true)
        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_bar)
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.DownloadButton,
            defStyleAttr,
            0
        )
        arr.recycle()
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        when (buttonState) {
            buttonState -> ButtonState.Clicked
            buttonState -> ButtonState.Loading
            else -> ButtonState.Completed
        }
        invalidate()
        return true
    }


    var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { _, _, new ->
        when (new) {
            ButtonState.Completed -> {
                isClickable = true
                buttonTextView.text = "READY FOR REVIEW"
                progressBar.layoutParams.width = 0
                animator.end()
            }
            ButtonState.Loading -> {
                isClickable = false
                buttonTextView.text = "LOADING..."
                animator = ofInt(0, width).apply {
                    duration = 2000
                    repeatMode = RESTART
                    repeatCount = INFINITE
                    addUpdateListener {
                        progressBar.layoutParams.width = animatedValue as Int
                        progressBar.requestLayout()
                    }
                    start()
                }
            }
            else -> {
                isClickable = true
                ButtonState.Clicked
                buttonTextView.text = "Download..."
                progressBar.layoutParams.width = 1
            }
        }

    }

    fun setState(state: ButtonState) {
        buttonState = state
    }
}