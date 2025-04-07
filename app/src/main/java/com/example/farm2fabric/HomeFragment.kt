package com.example.farm2fabric

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.farm2fabric.MainActivity

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
        Handler().postDelayed({
            motionLayout.transitionToEnd()
        }, 100)

        view.findViewById<CardView>(R.id.order_button).setOnClickListener{
            val intent = Intent(requireContext(), Order::class.java)
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

        return view
    }

}