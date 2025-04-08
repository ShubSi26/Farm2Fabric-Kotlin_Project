package com.example.farm2fabric

import PaymentAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Payments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ApiClient.makeRequest(
            context = this,
            path = "/getpayment",
            method = "GET",
        ) { success, response ->
            runOnUiThread {
                if (success && response != null) {
                    try {
                        val paymentsList = mutableListOf<PaymentItem>()
                        val jsonArray = JSONArray(response)

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val dateString = item.getString("date")
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                            val date = dateFormat.parse(dateString)

                            val payment = PaymentItem(
                                id = item.getString("_id"),
                                amount = item.getInt("amount"),
                                status = item.getString("status"),
                                customerid = item.getString("customerid"),
                                quantity = item.getInt("quantity"),
                                order_id = item.getString("order_id"),
                                paymentid = item.getString("paymentid"),
                                date = date ?: Date(),
                                consignment_id = item.getString("consignment_id")
                            )

                            paymentsList.add(payment)
                        }

                        recyclerView.adapter = PaymentAdapter(this,paymentsList)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "Parsing Error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
