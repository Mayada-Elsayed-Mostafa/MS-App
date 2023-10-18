package com.example.msapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val card_mri = view.findViewById<CardView>(R.id.card1!!)
        val card_edss = view.findViewById<CardView>(R.id.card2!!)
        val card_cis = view.findViewById<CardView>(R.id.card3!!)
        val card_monitoring = view.findViewById<CardView>(R.id.card4!!)

        card_mri.setOnClickListener {
            val fragment1 = MriFragment()
            navigateToFragment(fragment1)
        }

        card_edss.setOnClickListener {
            val fragment2 = EdssFragment()
            navigateToFragment(fragment2)
        }

        card_cis.setOnClickListener {
            val intent = Intent(context, CisActivity::class.java)
            startActivity(intent)
        }

        card_monitoring.setOnClickListener {
            val fragment4 = MonitoringFragment()
            navigateToFragment(fragment4)
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
