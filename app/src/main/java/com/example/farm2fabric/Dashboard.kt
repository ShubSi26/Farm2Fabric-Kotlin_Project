package com.example.farm2fabric

import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val motionLayout = findViewById<MotionLayout>(R.id.dashboard)
        Handler().postDelayed({
            motionLayout.transitionToEnd()
        }, 1000)
    }
}