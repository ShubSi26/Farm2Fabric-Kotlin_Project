package com.example.farm2fabric

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaymentDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_payment_detail)

        val consignmentId = intent.getStringExtra("consignment_id")
        val amount = intent.getStringExtra("amount")
        val status = intent.getStringExtra("status")
        val date = intent.getStringExtra("date")
        val orderId = intent.getStringExtra("order_id")
        val paymentId = intent.getStringExtra("payment_id")
        val quantity = intent.getStringExtra("quantity")

        val button  = findViewById<LinearLayout>(R.id.back)

        button.setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.tvDetailConsignmentId).text = consignmentId
        findViewById<TextView>(R.id.tvDetailAmount).text = "₹$amount"
        findViewById<TextView>(R.id.tvDetailStatus).text = status
        findViewById<TextView>(R.id.tvDetailDate).text = date
        findViewById<TextView>(R.id.tvDetailOrderId).text = orderId
        findViewById<TextView>(R.id.tvDetailPaymentId).text = paymentId
        findViewById<TextView>(R.id.tvDetailQuantity).text = "Qty: $quantity Kg"
    }
}