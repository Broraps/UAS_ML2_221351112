package com.raffiargianda.uas_ml2_221351112

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView

class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialCardView>(R.id.cardInfo).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_aboutFragment)
        }
        view.findViewById<MaterialCardView>(R.id.cardDataset).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_datasetFragment)
        }
        view.findViewById<MaterialCardView>(R.id.cardArsitektur).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_architectureFragment)
        }
        view.findViewById<MaterialCardView>(R.id.cardFitur).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_featuresFragment)
        }
        view.findViewById<MaterialCardView>(R.id.cardSimulasi).setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_simulationFragment)
        }
    }
}