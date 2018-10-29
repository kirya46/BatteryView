package com.example.kirillstoianov.batteryview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val batteryView = BatteryView(this)
//        val layoutParams = FrameLayout.LayoutParams(300, 700)
//        batteryView.layoutParams = layoutParams

        val container = findViewById<ViewGroup>(R.id.container)
        container.addView(batteryView)
    }
}
