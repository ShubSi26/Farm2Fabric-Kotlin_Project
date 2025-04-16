package com.example.farm2fabric

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class AcceptOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_accept_order)

        val consignmentId = intent.getStringExtra("consignmentId") ?: "N/A"
        val amount = intent.getStringExtra("amount") ?: "N/A"
        val status = intent.getStringExtra("status") ?: "N/A"
        val date = intent.getStringExtra("date") ?: "N/A"
        val paymentId = intent.getStringExtra("payment_id") ?: "N/A"
        val quantity = intent.getStringExtra("quantity") ?: "N/A"

        val inputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val outputFormatD = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val outputFormatT = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val datet = inputFormat.parse(date)
        val formattedDateOnly = outputFormatD.format(datet!!)
        val formattedTimeOnly = outputFormatT.format(datet!!)

        findViewById<TextView>(R.id.valueConsignmentId2).text = consignmentId
        findViewById<TextView>(R.id.valueWeight2).text = "${quantity} Kg"
        findViewById<TextView>(R.id.valuePrice2).text = "Rs. ${amount}"
        findViewById<TextView>(R.id.valuePaymentId2).text = paymentId
        findViewById<TextView>(R.id.Ctitle2).text = consignmentId
        findViewById<TextView>(R.id.valueDate2).text = formattedDateOnly
        findViewById<TextView>(R.id.valueTime2).text = formattedTimeOnly

        findViewById<LinearLayout>(R.id.back7).setOnClickListener {
            finish()
        }

        val acceptButton = findViewById<Button>(R.id.acceptorder)
        val txid = findViewById<EditText>(R.id.txid)
        val tralinlayout = findViewById<LinearLayout>(R.id.updateorder)

        if(status == "step0"){
            acceptButton.visibility = View.VISIBLE
        }else{
            acceptButton.visibility = View.GONE
        }

        if(status == "step1"){
            tralinlayout.visibility = View.VISIBLE
        }else{
            tralinlayout.visibility = View.GONE
        }

        acceptButton.setOnClickListener {

            val jsonObject = JSONObject()
            jsonObject.put("consignmentid", consignmentId)

            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val body = jsonObject.toString().toRequestBody(mediaType)

            ApiClient.makeRequest(
                context = this,
                path = "/acceptorder",
                method = "POST",
                body = body
            ) { success, response ->
                this.runOnUiThread {
                    if (success) {
                        Toast.makeText(this,"Order Accepted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,"Server Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        findViewById<Button>(R.id.update).setOnClickListener {
            if(txid.text.isEmpty()) {
                Toast.makeText(this, "Please enter tracking id", Toast.LENGTH_SHORT).show()
            }else{
                val jsonObject = JSONObject()
                jsonObject.put("trackingid", txid.text.toString())
                jsonObject.put("consignmentid", consignmentId)

                val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = jsonObject.toString().toRequestBody(mediaType)

                ApiClient.makeRequest(
                    context = this,
                    path = "/updateorder",
                    method = "POST",
                    body = body
                ) { success, response ->
                    this.runOnUiThread {
                        if (success) {
                            Toast.makeText(this,"Order Updated", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,"Server Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}