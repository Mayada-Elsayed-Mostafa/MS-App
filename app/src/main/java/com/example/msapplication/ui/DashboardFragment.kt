package com.example.msapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.msapplication.R

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val cardMri = view.findViewById<CardView>(R.id.card1!!)
        val cardEdss = view.findViewById<CardView>(R.id.card2!!)
        val cardCis = view.findViewById<CardView>(R.id.card3!!)
        val cardMonitoring = view.findViewById<CardView>(R.id.card4!!)

        cardMri.setOnClickListener {
            val intent = Intent(context, MriActivity::class.java)
            startActivity(intent)
        }

        cardEdss.setOnClickListener {
            val intent = Intent(context, EdssActivity::class.java)
            startActivity(intent)
        }

        cardCis.setOnClickListener {
            val intent = Intent(context, CisActivity::class.java)
            startActivity(intent)
        }

        cardMonitoring.setOnClickListener {
            val intent = Intent(context, MonitoringActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    private fun navigateToFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null) // If you want to add to the back stack
        fragmentTransaction.commit()
    }

}