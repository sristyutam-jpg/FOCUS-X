package com.focusx.app

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import android.graphics.Typeface

class MainActivity : Activity() {

    private var seconds = 25 * 60
    private var running = false
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var timerText: TextView

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (running && seconds > 0) {
                seconds--
                updateTimer()
                handler.postDelayed(this, 1000)
            } else {
                running = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(25, 30, 25, 30)
            setBackgroundColor(Color.rgb(12, 12, 18))
        }

        val title = TextView(this).apply {
            text = "FOCUS X"
            textSize = 32f
            setTextColor(Color.rgb(170, 100, 255))
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        val subtitle = TextView(this).apply {
            text = "Take Control of Your Time"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        timerText = TextView(this).apply {
            textSize = 55f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 50, 0, 50)
        }

        val start = Button(this).apply {
            text = "START FOCUS"
            setOnClickListener {
                if (!running && seconds > 0) {
                    running = true
                    handler.post(timerRunnable)
                }
            }
        }

        val pause = Button(this).apply {
            text = "PAUSE"
            setOnClickListener {
                running = false
                handler.removeCallbacks(timerRunnable)
            }
        }

        val reset = Button(this).apply {
            text = "RESET"
            setOnClickListener {
                running = false
                handler.removeCallbacks(timerRunnable)
                seconds = 25 * 60
                updateTimer()
            }
        }

        val goal = TextView(this).apply {
            text = "DAILY GOAL\nComplete your 25-minute focus session."
            textSize = 17f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 20)
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(timerText)
        layout.addView(start)
        layout.addView(pause)
        layout.addView(reset)
        layout.addView(goal)

        updateTimer()
        setContentView(layout)
    }

    private fun updateTimer() {
        val minutes = seconds / 60
        val secs = seconds % 60

        timerText.text = String.format(
            "%02d:%02d", minutes, secs
        )
    }
}
