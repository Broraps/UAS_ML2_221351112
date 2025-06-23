package com.raffiargianda.uas_ml2_221351112

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnMenuAbout).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_aboutFragment)
        }

        view.findViewById<Button>(R.id.btnMenuFeatures).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_featuresFragment)
        }

        view.findViewById<Button>(R.id.btnMenuArchitecture).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_architectureFragment)
        }

        view.findViewById<Button>(R.id.btnMenuSimulation).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_simulationFragment)
        }
    }
}