package com.raffiargianda.uas_ml2_221351112

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulationFragment : Fragment() {

    private lateinit var interpreter: Interpreter
    private val mModelPath = "lung_prediction_compatible.tflite"

    private lateinit var resultText: TextView
    private lateinit var checkButton: Button
    private lateinit var resultCard: CardView
    private lateinit var edtAge: TextInputEditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var sliderAirPollution: Slider
    private lateinit var sliderAlcoholUse: Slider
    private lateinit var sliderDustAllergy: Slider
    private lateinit var sliderOccuPationalHazards: Slider
    private lateinit var sliderGeneticRisk: Slider
    private lateinit var sliderChronicLungDisease: Slider
    private lateinit var sliderBalancedDiet: Slider
    private lateinit var sliderObesity: Slider
    private lateinit var sliderSmoking: Slider
    private lateinit var sliderPassiveSmoker: Slider
    private lateinit var sliderChestPain: Slider
    private lateinit var sliderCoughingOfBlood: Slider
    private lateinit var sliderFatigue: Slider
    private lateinit var sliderWeightLoss: Slider
    private lateinit var sliderShortnessOfBreath: Slider
    private lateinit var sliderWheezing: Slider
    private lateinit var sliderSwallowingDifficulty: Slider
    private lateinit var sliderClubbingOfFingerNails: Slider
    private lateinit var sliderFrequentCold: Slider
    private lateinit var sliderDryCough: Slider
    private lateinit var sliderSnoring: Slider
    private lateinit var tvAirPollutionLabel: TextView
    private lateinit var tvAlcoholUseLabel: TextView
    private lateinit var tvDustAllergyLabel: TextView
    private lateinit var tvOccuPationalHazardsLabel: TextView
    private lateinit var tvGeneticRiskLabel: TextView
    private lateinit var tvChronicLungDiseaseLabel: TextView
    private lateinit var tvBalancedDietLabel: TextView
    private lateinit var tvObesityLabel: TextView
    private lateinit var tvSmokingLabel: TextView
    private lateinit var tvPassiveSmokerLabel: TextView
    private lateinit var tvChestPainLabel: TextView
    private lateinit var tvCoughingOfBloodLabel: TextView
    private lateinit var tvFatigueLabel: TextView
    private lateinit var tvWeightLossLabel: TextView
    private lateinit var tvShortnessOfBreathLabel: TextView
    private lateinit var tvWheezingLabel: TextView
    private lateinit var tvSwallowingDifficultyLabel: TextView
    private lateinit var tvClubbingOfFingerNailsLabel: TextView
    private lateinit var tvFrequentColdLabel: TextView
    private lateinit var tvDryCoughLabel: TextView
    private lateinit var tvSnoringLabel: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simulation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInterpreter()
        initializeViews(view)
        setupSliderListeners()
        checkButton.setOnClickListener { runPrediction() }
    }

    private fun runPrediction() {
        val ageText = edtAge.text.toString()
        if (ageText.isEmpty()) {
            Toast.makeText(requireContext(), "Harap isi umur terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        val rawInputs = gatherRawInputs(ageText)

        val result = doInference(rawInputs)

        displayResult(result)
    }

    private fun minMaxScale(value: Float, min: Float, max: Float): Float {
        return (value - min) / (max - min)
    }

    private fun gatherRawInputs(ageText: String): FloatArray {
        val age = ageText.toFloat()
        val gender = if (radioGroupGender.checkedRadioButtonId == R.id.radioWanita) 2.0f else 1.0f

        return floatArrayOf(
            minMaxScale(age, 14f, 73f),
            minMaxScale(gender, 1f, 2f),
            minMaxScale(sliderAirPollution.value, 1f, 8f),
            minMaxScale(sliderAlcoholUse.value, 1f, 8f),
            minMaxScale(sliderDustAllergy.value, 1f, 8f),
            minMaxScale(sliderOccuPationalHazards.value, 1f, 8f),
            minMaxScale(sliderGeneticRisk.value, 1f, 7f),
            minMaxScale(sliderChronicLungDisease.value, 1f, 7f),
            minMaxScale(sliderBalancedDiet.value, 1f, 7f),
            minMaxScale(sliderObesity.value, 1f, 7f),
            minMaxScale(sliderSmoking.value, 1f, 8f),
            minMaxScale(sliderPassiveSmoker.value, 1f, 8f),
            minMaxScale(sliderChestPain.value, 1f, 9f),
            minMaxScale(sliderCoughingOfBlood.value, 1f, 9f),
            minMaxScale(sliderFatigue.value, 1f, 9f),
            minMaxScale(sliderWeightLoss.value, 1f, 8f),
            minMaxScale(sliderShortnessOfBreath.value, 1f, 9f),
            minMaxScale(sliderWheezing.value, 1f, 8f),
            minMaxScale(sliderSwallowingDifficulty.value, 1f, 8f),
            minMaxScale(sliderClubbingOfFingerNails.value, 1f, 9f),
            minMaxScale(sliderFrequentCold.value, 1f, 7f),
            minMaxScale(sliderDryCough.value, 1f, 7f),
            minMaxScale(sliderSnoring.value, 1f, 7f)
        )
    }


    private fun displayResult(resultCode: Int) {
        activity?.runOnUiThread {
            showResultDialog(resultCode)
        }
    }



    private fun initializeViews(view: View) {
        checkButton = view.findViewById(R.id.btnCheck)
        edtAge = view.findViewById(R.id.edtAge)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)

        sliderAirPollution = view.findViewById(R.id.sliderAirPollution); tvAirPollutionLabel = view.findViewById(R.id.tvAirPollutionLabel)
        sliderAlcoholUse = view.findViewById(R.id.sliderAlcoholUse); tvAlcoholUseLabel = view.findViewById(R.id.tvAlcoholUseLabel)
        sliderDustAllergy = view.findViewById(R.id.sliderDustAllergy); tvDustAllergyLabel = view.findViewById(R.id.tvDustAllergyLabel)
        sliderOccuPationalHazards = view.findViewById(R.id.sliderOccuPationalHazards); tvOccuPationalHazardsLabel = view.findViewById(R.id.tvOccuPationalHazardsLabel)
        sliderGeneticRisk = view.findViewById(R.id.sliderGeneticRisk); tvGeneticRiskLabel = view.findViewById(R.id.tvGeneticRiskLabel)
        sliderChronicLungDisease = view.findViewById(R.id.sliderChronicLungDisease); tvChronicLungDiseaseLabel = view.findViewById(R.id.tvChronicLungDiseaseLabel)
        sliderBalancedDiet = view.findViewById(R.id.sliderBalancedDiet); tvBalancedDietLabel = view.findViewById(R.id.tvBalancedDietLabel)
        sliderObesity = view.findViewById(R.id.sliderObesity); tvObesityLabel = view.findViewById(R.id.tvObesityLabel)
        sliderSmoking = view.findViewById(R.id.sliderSmoking); tvSmokingLabel = view.findViewById(R.id.tvSmokingLabel)
        sliderPassiveSmoker = view.findViewById(R.id.sliderPassiveSmoker); tvPassiveSmokerLabel = view.findViewById(R.id.tvPassiveSmokerLabel)
        sliderChestPain = view.findViewById(R.id.sliderChestPain); tvChestPainLabel = view.findViewById(R.id.tvChestPainLabel)
        sliderCoughingOfBlood = view.findViewById(R.id.sliderCoughingOfBlood); tvCoughingOfBloodLabel = view.findViewById(R.id.tvCoughingOfBloodLabel)
        sliderFatigue = view.findViewById(R.id.sliderFatigue); tvFatigueLabel = view.findViewById(R.id.tvFatigueLabel)
        sliderWeightLoss = view.findViewById(R.id.sliderWeightLoss); tvWeightLossLabel = view.findViewById(R.id.tvWeightLossLabel)
        sliderShortnessOfBreath = view.findViewById(R.id.sliderShortnessOfBreath); tvShortnessOfBreathLabel = view.findViewById(R.id.tvShortnessOfBreathLabel)
        sliderWheezing = view.findViewById(R.id.sliderWheezing); tvWheezingLabel = view.findViewById(R.id.tvWheezingLabel)
        sliderSwallowingDifficulty = view.findViewById(R.id.sliderSwallowingDifficulty); tvSwallowingDifficultyLabel = view.findViewById(R.id.tvSwallowingDifficultyLabel)
        sliderClubbingOfFingerNails = view.findViewById(R.id.sliderClubbingOfFingerNails); tvClubbingOfFingerNailsLabel = view.findViewById(R.id.tvClubbingOfFingerNailsLabel)
        sliderFrequentCold = view.findViewById(R.id.sliderFrequentCold); tvFrequentColdLabel = view.findViewById(R.id.tvFrequentColdLabel)
        sliderDryCough = view.findViewById(R.id.sliderDryCough); tvDryCoughLabel = view.findViewById(R.id.tvDryCoughLabel)
        sliderSnoring = view.findViewById(R.id.sliderSnoring); tvSnoringLabel = view.findViewById(R.id.tvSnoringLabel)
    }

    private fun setupSliderListeners() {
        sliderAirPollution.addOnChangeListener { _, value, _ -> tvAirPollutionLabel.text = "Polusi Udara (1-8): ${value.toInt()}" }
        sliderAlcoholUse.addOnChangeListener { _, value, _ -> tvAlcoholUseLabel.text = "Penggunaan Alkohol (1-8): ${value.toInt()}" }
        sliderDustAllergy.addOnChangeListener { _, value, _ -> tvDustAllergyLabel.text = "Alergi Debu (1-8): ${value.toInt()}" }
        sliderOccuPationalHazards.addOnChangeListener { _, value, _ -> tvOccuPationalHazardsLabel.text = "Bahaya Pekerjaan (1-8): ${value.toInt()}" }
        sliderGeneticRisk.addOnChangeListener { _, value, _ -> tvGeneticRiskLabel.text = "Risiko Genetik (1-7): ${value.toInt()}" }
        sliderChronicLungDisease.addOnChangeListener { _, value, _ -> tvChronicLungDiseaseLabel.text = "Penyakit Paru Kronis (1-7): ${value.toInt()}" }
        sliderBalancedDiet.addOnChangeListener { _, value, _ -> tvBalancedDietLabel.text = "Diet Seimbang (1-7): ${value.toInt()}" }
        sliderObesity.addOnChangeListener { _, value, _ -> tvObesityLabel.text = "Obesitas (1-7): ${value.toInt()}" }
        sliderSmoking.addOnChangeListener { _, value, _ -> tvSmokingLabel.text = "Merokok (1-8): ${value.toInt()}" }
        sliderPassiveSmoker.addOnChangeListener { _, value, _ -> tvPassiveSmokerLabel.text = "Perokok Pasif (1-8): ${value.toInt()}" }
        sliderChestPain.addOnChangeListener { _, value, _ -> tvChestPainLabel.text = "Nyeri Dada (1-9): ${value.toInt()}" }
        sliderCoughingOfBlood.addOnChangeListener { _, value, _ -> tvCoughingOfBloodLabel.text = "Batuk Darah (1-9): ${value.toInt()}" }
        sliderFatigue.addOnChangeListener { _, value, _ -> tvFatigueLabel.text = "Kelelahan (1-9): ${value.toInt()}" }
        sliderWeightLoss.addOnChangeListener { _, value, _ -> tvWeightLossLabel.text = "Penurunan Berat Badan (1-8): ${value.toInt()}" }
        sliderShortnessOfBreath.addOnChangeListener { _, value, _ -> tvShortnessOfBreathLabel.text = "Sesak Napas (1-9): ${value.toInt()}" }
        sliderWheezing.addOnChangeListener { _, value, _ -> tvWheezingLabel.text = "Mengi (1-8): ${value.toInt()}" }
        sliderSwallowingDifficulty.addOnChangeListener { _, value, _ -> tvSwallowingDifficultyLabel.text = "Kesulitan Menelan (1-8): ${value.toInt()}" }
        sliderClubbingOfFingerNails.addOnChangeListener { _, value, _ -> tvClubbingOfFingerNailsLabel.text = "Kuku Jari Tabuh (1-9): ${value.toInt()}" }
        sliderFrequentCold.addOnChangeListener { _, value, _ -> tvFrequentColdLabel.text = "Sering Pilek (1-7): ${value.toInt()}" }
        sliderDryCough.addOnChangeListener { _, value, _ -> tvDryCoughLabel.text = "Batuk Kering (1-7): ${value.toInt()}" }
        sliderSnoring.addOnChangeListener { _, value, _ -> tvSnoringLabel.text = "Mendengkur (1-7): ${value.toInt()}" }
    }

    private fun initInterpreter() {
        val options = Interpreter.Options()
        options.setNumThreads(4)
        interpreter = Interpreter(loadModelFile(requireContext().assets, mModelPath), options)
    }

    private fun doInference(inputVal: FloatArray): Int {
        val output = Array(1) { FloatArray(3) }
        interpreter.run(inputVal, output)
        Log.d("InferenceResult", "Raw output: ${output[0].toList()}")
        return output[0].indexOfFirst { it == output[0].maxOrNull() }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun showResultDialog(level: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.result_dialog, null)
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        val cardContainer = dialogView.findViewById<CardView>(R.id.cardContainer)
        val txtDialogResult = dialogView.findViewById<TextView>(R.id.txtDialogResult)
        val imgStatus = dialogView.findViewById<ImageView>(R.id.imgStatus)

        when (level) {
            1 -> {
                cardContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.level_low))
                txtDialogResult.text = "Hasil Prediksi Terkena Kanker : Rendah, Pertahankan Kesehatan Kamu Ya!!!!!"
                imgStatus.setImageResource(R.drawable.ic_good)
            }
            2 -> {
                cardContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.level_medium))
                txtDialogResult.text = "Hasil Prediksi Terkena Kanker : Sedang, Aduhhh Jaga Kesehatan Mu Ya!!!!"
                imgStatus.setImageResource(R.drawable.ic_warning)
            }
            0 -> {
                cardContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.level_high))
                txtDialogResult.text = "Hasil Prediksi Terkena Kanker: Tinggi, Waduh Bahaya Nih, Coba Cek Kesehatan Mu Segera!!!"
                imgStatus.setImageResource(R.drawable.ic_error)
            }
            else -> {
                cardContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_background_secondary))
                txtDialogResult.text = "Hasil Tidak Diketahui"
                imgStatus.setImageResource(R.drawable.ic_warning)
            }
        }

        dialog.show()
    }


}
