package com.goodayedi.loadme

import android.animation.ValueAnimator.*
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadButton: DownloadButton = findViewById(R.id.button_download)
        val progressBar: View = findViewById(R.id.progress_bar)
        var status = LoadingStatus.NORMAL
        downloadButton.setText(status)

        downloadButton.setOnClickListener { view ->
            status = status.next()
            (view as DownloadButton).setText(status)
            view.isEnabled = false
            val widthAnimator = ofInt(0, view.width)
            widthAnimator.duration = 1000
            widthAnimator.interpolator = DecelerateInterpolator()
            widthAnimator.addUpdateListener { animator ->
                progressBar.layoutParams.width = animator.animatedValue as Int
                progressBar.requestLayout()
            }

            widthAnimator.doOnEnd {
                status = status.next()
                view.setText(status)
                view.isEnabled = true
                status = status.next()
            }

            widthAnimator.start()
        }

    }

}

