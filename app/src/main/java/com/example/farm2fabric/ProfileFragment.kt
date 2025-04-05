package com.example.farm2fabric

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        ApiClient.makeRequest(
            context = requireContext(),
            path = "/userdetail",
            method = "GET"
        ) { success, response ->
            requireActivity().runOnUiThread {
                if (success) {
                    Log.d("data",response+"")
                    val resp = JSONObject(response)
                    val data = resp.getString("data")
                    val jsonObject = JSONObject(data)
                    val name = jsonObject.getString("name")
                    val email = jsonObject.getString("email")
                    val role = jsonObject.getString("role")
                    view.findViewById<TextView>(R.id.profile_name).text = name
                    view.findViewById<TextView>(R.id.profile_email).text = email
                    view.findViewById<TextView>(R.id.profile_role).text = role
                } else {
                    Toast.makeText(requireContext(),"Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

}