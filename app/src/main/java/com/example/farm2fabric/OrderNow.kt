package com.example.farm2fabric

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.farm2fabric.MainActivity
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.razorpay.PaymentResultListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class OrderNow : AppCompatActivity(),PaymentResultWithDataListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_now)

        var quantity = 0
        var price = 0

        val upbutton = findViewById<Button>(R.id.upbutton)
        val downbutton = findViewById<Button>(R.id.down)
        val quantityivew = findViewById<TextView>(R.id.quantity)
        val priceview = findViewById<TextView>(R.id.price)
        val checkoutbutton = findViewById<Button>(R.id.checkoutButton)

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

        checkoutbutton.setOnClickListener {
            if(quantity > 0){
                val jsonObject = JSONObject()
                jsonObject.put("quantity", quantity)
                jsonObject.put("price", price)

                val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = jsonObject.toString().toRequestBody(mediaType)

                ApiClient.makeRequest(
                    context = this,
                    path = "/order/createorder",
                    method = "POST",
                    body = body
                ) { success, response ->
                    this.runOnUiThread {
                        if (success) {
                            startPayment(response.toString())
                        } else {
                            Toast.makeText(this,"Server Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun startPayment(response:String){
        val co = Checkout()

        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val phone = sharedPreferences.getString("phone", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""

        val options = JSONObject(response)
        options.put("name", "Farm2Fabric")
        options.put("description", "Order Payment")
        options.put("theme.color", "#3399cc")
        val prefill = JSONObject()
        prefill.put("email",email)
        prefill.put("contact",phone)
        options.put("prefill",prefill)

        val key = options.getString("key")
        co.setKeyID(key)

        co.open(this,options)
    }

    override fun onPaymentError(errorCode: Int, response: String, p2: PaymentData) {
        Toast.makeText(this,"Payment Failed", Toast.LENGTH_SHORT).show()
    }
    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData) {

        val jsonObject = paymentData.getData()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = jsonObject.toString().toRequestBody(mediaType)

        ApiClient.makeRequest(
            context = this,
            path = "/order/verify",
            method = "POST",
            body = body
        ) { success, response ->
            this.runOnUiThread {
                if (success) {
                    Toast.makeText(this,"Order Created", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"Server Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}