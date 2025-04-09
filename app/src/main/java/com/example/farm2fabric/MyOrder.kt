package com.example.farm2fabric

import ConsignmentAdapter
import PaymentAdapter
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MyOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val recyclerView = findViewById<RecyclerView>(R.id.OrderrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val button = findViewById<LinearLayout>(R.id.back3)

        button.setOnClickListener {
            finish()
        }

        ApiClient.makeRequest(
            context = this,
            path = "/getconsignment",
            method = "GET",
        ) { success, response ->
            runOnUiThread {
                if (success && response != null) {
                    try {
                        val paymentsList = mutableListOf<ConsignmentItem>()
                        val jsonArray = JSONArray(response)

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val dateString = item.getString("date")
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                            val date = dateFormat.parse(dateString)

                            val payment = ConsignmentItem(
                                id = item.getString("_id"),
                                price = item.getInt("price"),
                                status = item.getString("status"),
                                customerid = item.getString("customerid"),
                                quantity = item.getInt("quantity"),
                                consignmentId = item.getString("consignment_id"),
                                paymentid = item.getString("paymentid"),
                                date = date ?: Date(),
                                paymentstatus = item.getString("paymentstatus"),
                                trackingid = item.getString("trackingid"),
                            )

                            paymentsList.add(payment)
                        }

                        recyclerView.adapter = ConsignmentAdapter(this,paymentsList)

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