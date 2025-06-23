package com.raffiargianda.uas_ml2_221351112

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeaturesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_features, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daftarFitur = siapkanData()

        val recyclerView: RecyclerView = view.findViewById(R.id.rvFitur)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = FiturAdapter(daftarFitur)
        recyclerView.adapter = adapter
    }

    private fun siapkanData(): List<Fitur> {
        val daftarStringId = listOf(
            R.string.age, R.string.gender, R.string.polution, R.string.alkohol,
            R.string.alergic, R.string.dangerous_work, R.string.gen_risk, R.string.lung_cronis,
            R.string.diet, R.string.obesity, R.string.smoking, R.string.pasif_smok,
            R.string.chest_pain, R.string.blood_cough, R.string.tiring, R.string.loss_weight,
            R.string.sesak, R.string.mengi, R.string.kesulitan_menelan, R.string.menggiti_jari, R.string.frequent_cold,
            R.string.dry_cough, R.string.snoring
        )

        return daftarStringId.map { id ->
            Fitur(deskripsi = getString(id))
        }
    }
}