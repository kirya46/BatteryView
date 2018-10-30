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
//        batteryView.maxSectionCount =20

        findViewById<Button>(R.id.btnIncrease).setOnClickListener {
            batteryView.inscrease()
        }

        findViewById<Button>(R.id.btnDecrease).setOnClickListener {
            batteryView.decrease()
        }

        val container = findViewById<ViewGroup>(R.id.container)
        container.addView(batteryView)
    }
}
