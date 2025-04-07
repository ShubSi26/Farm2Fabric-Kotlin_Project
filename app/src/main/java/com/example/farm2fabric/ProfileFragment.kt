package com.example.farm2fabric

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
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

        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val role = sharedPreferences.getString("role", "") ?: ""
        val phone = sharedPreferences.getString("phone", "") ?: ""
        val address = sharedPreferences.getString("address", "") ?: ""
        val occupation = sharedPreferences.getString("occupation", "") ?: ""

        // Update name row
        val nameRow = view.findViewById<LinearLayout>(R.id.name_row)
        nameRow.findViewById<TextView>(R.id.label).text = "Name"
        nameRow.findViewById<TextView>(R.id.value).text = name

        // Update email row
        val emailRow = view.findViewById<LinearLayout>(R.id.email_row)
        emailRow.findViewById<TextView>(R.id.label).text = "Email"
        emailRow.findViewById<TextView>(R.id.value).text = email

        // Update phone row
        val phoneRow = view.findViewById<LinearLayout>(R.id.phone_row)
        phoneRow.findViewById<TextView>(R.id.label).text = "Phone"
        phoneRow.findViewById<TextView>(R.id.value).text = phone

        // Update role row
        val roleRow = view.findViewById<LinearLayout>(R.id.role_row)
        roleRow.findViewById<TextView>(R.id.label).text = "Role"
        roleRow.findViewById<TextView>(R.id.value).text = role

        val addressRow = view.findViewById<LinearLayout>(R.id.address_row)
        addressRow.findViewById<TextView>(R.id.label).text = "Address"
        addressRow.findViewById<TextView>(R.id.value).text = address

        val occupationRow = view.findViewById<LinearLayout>(R.id.occupation_row)
        occupationRow.findViewById<TextView>(R.id.label).text = "Occupation"
        occupationRow.findViewById<TextView>(R.id.value).text = occupation

        return view
    }


}