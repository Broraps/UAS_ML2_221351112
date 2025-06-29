package com.raffiargianda.uas_ml2_221351112

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class DatasetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dataset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tableLayout = view.findViewById<TableLayout>(R.id.tableLayoutDataset)
        populateTableFromCsv(tableLayout)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun populateTableFromCsv(tableLayout: TableLayout) {
        try {
            val inputStream = resources.openRawResource(R.raw.cancer_patient_data)
            val reader = BufferedReader(InputStreamReader(inputStream))

            val headerLine = reader.readLine()
            if (headerLine != null) {
                val headers = headerLine.split(',')
                val headerRow = TableRow(context)
                headerRow.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )

                headers.forEach { headerText ->
                    val textView = TextView(context).apply {
                        text = headerText.trim()
                        setTypeface(null, Typeface.BOLD)
                        setPadding(24)
                        gravity = Gravity.CENTER
                        setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.text_secondary_dark))
                    }
                    headerRow.addView(textView)
                }
                tableLayout.addView(headerRow)
            }

            var line: String?
            var lineCount = 0
            while (reader.readLine().also { line = it } != null && lineCount < 5) {
                val dataRow = TableRow(context)
                dataRow.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                val rowData = line!!.split(',')

                rowData.forEach { cellData ->
                    val textView = TextView(context).apply {
                        text = cellData.trim()
                        setPadding(24)
                        gravity = Gravity.CENTER
                    }
                    dataRow.addView(textView)
                }
                tableLayout.addView(dataRow)
                lineCount++
            }
            reader.close()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error: Gagal membaca file CSV.", Toast.LENGTH_LONG).show()
        }
    }
}