package com.example.farm2fabric

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.farm2fabric.BuildConfig
import okhttp3.*
import java.io.IOException

object ApiClient {

    private val client = OkHttpClient()
    private const val BASE_URL = BuildConfig.API_LINK

    fun makeRequest(
        context: Context,
        path: String,
        method: String = "GET",
        body: RequestBody? = null,
        callback: (success: Boolean, response: String?) -> Unit
    ) {
        val url = "$BASE_URL$path"

        val requestBuilder = Request.Builder().url(url)

        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("auth_token", "") ?: ""

        requestBuilder.addHeader("authorization", authToken)

        when (method.uppercase()) {
            "GET" -> requestBuilder.get()
            "POST" -> requestBuilder.post(body ?: RequestBody.create(null, ByteArray(0)))
            "PUT" -> requestBuilder.put(body ?: RequestBody.create(null, ByteArray(0)))
            "DELETE" -> requestBuilder.delete(body ?: RequestBody.create(null, ByteArray(0)))
            else -> {
                callback(false, "Unsupported HTTP method")
                return
            }
        }

        val request = requestBuilder.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    callback(it.isSuccessful, it.body?.string())
                }
            }
        })
    }
}
