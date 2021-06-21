package com.goodayedi.loadme

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class DownloadButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var buttonTextView: TextView
    private var progressBar: View
    private var status: LoadingStatus

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.progress_button, this, true)
        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_bar)
        status = LoadingStatus.NORMAL
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.DownloadButton,
            defStyleAttr,
            0
        )
        setText(status)
        arr.recycle()
    }


    fun setText(status: LoadingStatus) {
        this.status = status
        buttonTextView.text = resources.getString(this.status.label)
    }
}