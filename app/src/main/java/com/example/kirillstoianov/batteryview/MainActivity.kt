package com.example.kirillstoianov.batteryview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ViewGroup>(R.id.container).addView(BatteryView(this))
    }
}
