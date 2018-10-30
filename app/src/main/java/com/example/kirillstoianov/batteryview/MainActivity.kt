package com.example.kirillstoianov.batteryview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val batteryView = BatteryView(this)
//        val layoutParams = FrameLayout.LayoutParams(300, 700)
//        batteryView.layoutParams = layoutParams

        val container = findViewById<ViewGroup>(R.id.container)
        container.addView(batteryView)


        findViewById<Button>(R.id.btnIncrease).setOnClickListener {
            count += 1
            batteryView.fillSectionCount = count
        }

        findViewById<Button>(R.id.btnDecrease).setOnClickListener {
            count -= 1
            batteryView.fillSectionCount = count
        }
    }
}
