package com.example.farm2fabric

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.farm2fabric.MainActivity
import android.util.Log
import android.widget.TextView


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val motionLayout = view.findViewById<MotionLayout>(R.id.dashboard)

        val farmerlyout = view.findViewById<LinearLayout>(R.id.farmerlinearlayout)
        val customerlayout = view.findViewById<LinearLayout>(R.id.customerlinearlayout)

        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val role = sharedPreferences.getString("role", "") ?: ""
        val name = sharedPreferences.getString("name", "") ?: ""

        if (role == "Farmer") {
            farmerlyout.visibility = View.VISIBLE
            customerlayout.visibility = View.GONE
        } else if (role == "Customer") {
            customerlayout.visibility = View.VISIBLE
            farmerlyout.visibility = View.GONE
        }

        view.findViewById<TextView>(R.id.greetings).text = "$name"

        Handler().postDelayed({
            motionLayout.transitionToEnd()
        }, 100)

        view.findViewById<CardView>(R.id.order_button).setOnClickListener{
            val intent = Intent(requireContext(), MyOrder::class.java)
            startActivity(intent)
        }

        view.findViewById<CardView>(R.id.order_now_button).setOnClickListener{
            val intent = Intent(requireContext(), OrderNow::class.java)
            startActivity(intent)
        }

        view.findViewById<CardView>(R.id.payment_button).setOnClickListener{
            val intent = Intent(requireContext(), Payments::class.java)
            startActivity(intent)
        }

        view.findViewById<CardView>(R.id.view_order_button).setOnClickListener {
            val intent = Intent(requireContext(), UnacceptedOrders::class.java)
            startActivity(intent)
        }

        view.findViewById<CardView>(R.id.my_orders).setOnClickListener {
            val intent = Intent(requireContext(), MyOrder::class.java)
            startActivity(intent)
        }

        view.findViewById<CardView>(R.id.mypayment).setOnClickListener {
            val intent = Intent(requireContext(), Payments::class.java)
            startActivity(intent)
        }

        return view
    }

}