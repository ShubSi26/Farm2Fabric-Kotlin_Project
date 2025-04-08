package com.example.farm2fabric

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderNow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_now)

        var quantity = 0
        var price = 0

        val upbutton = findViewById<Button>(R.id.upbutton)
        val downbutton = findViewById<Button>(R.id.down)
        val quantityivew = findViewById<TextView>(R.id.quantity)
        val priceview = findViewById<TextView>(R.id.price)

        upbutton.setOnClickListener {
            quantity += 1
            price += 100
            quantityivew.text = quantity.toString()
            priceview.text = price.toString()
        }

        downbutton.setOnClickListener {
            if (quantity > 0) {
                quantity -= 1
                price -= 100
                quantityivew.text = quantity.toString()
                priceview.text = price.toString()
            }
        }

    }
}