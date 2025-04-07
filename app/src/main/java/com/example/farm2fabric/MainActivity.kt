package com.example.farm2fabric

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.example.farm2fabric.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val motionview = findViewById<MotionLayout>(R.id.main)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        ApiClient.makeRequest(
            context = this,
            path = "/userdetail",
            method = "GET"
        ) { success, response ->
            this.runOnUiThread {
                if (success) {
                    Log.d("data",response+"")
                    val resp = JSONObject(response)
                    val data = resp.getString("data")
                    val jsonObject = JSONObject(data)
                    val name = jsonObject.getString("name")
                    val email = jsonObject.getString("email")
                    val role = jsonObject.getString("role")
                    val address = jsonObject.getString("address")
                    val occupation  = jsonObject.getString("occupation")
                    val phone = jsonObject.getString("phone")
                    with(sharedPreferences.edit()) {
                        putString("name", name)
                        putString("email", email)
                        putString("role", role)
                        putString("address", address)
                        putString("occupation", occupation)
                        putString("phone", phone)
                        apply()
                    }
                    val intent = Intent(this@MainActivity, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    motionview.transitionToEnd()
                }
            }
        }
    }

    fun submit(view: View) {
        val nameField = findViewById<EditText>(R.id.input_email)
        val passwordField = findViewById<EditText>(R.id.input_password)

        val name = nameField.text.toString().trim()
        val password = passwordField.text.toString().trim()

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both name and password", Toast.LENGTH_SHORT).show()
            return
        }

        val apiUrl = BuildConfig.API_LINK + "/login" // Read from gradle.properties

        val jsonObject = JSONObject()
        jsonObject.put("email", name)
        jsonObject.put("password", password)

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(apiUrl)
            .post(body)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    val responseBody = response.body?.string() ?: "Empty response"

                    if (response.isSuccessful) {
                        try {
                            // Parse JSON response
                            val jsonObject = JSONObject(responseBody)
                            val token = jsonObject.getString("token")

                            // Save token in SharedPreferences
                            val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putString("auth_token", token)
                                apply()
                            }

                            val intent = intent
                            finish()
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, "Response Error", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Server Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }
}
