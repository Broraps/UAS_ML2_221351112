package com.raffiargianda.uas_ml2_221351112

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FiturAdapter(private val daftarFitur: List<Fitur>) :
    RecyclerView.Adapter<FiturAdapter.FiturViewHolder>() {

    class FiturViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDeskripsi: TextView = view.findViewById(R.id.tvFitur)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiturViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fitur, parent, false)
        return FiturViewHolder(view)
    }

    override fun onBindViewHolder(holder: FiturViewHolder, position: Int) {
        val fitur = daftarFitur[position]
        val nomor = position + 1

        val deskripsi = fitur.deskripsi
        val baris = deskripsi.split("\n")

        val judul = "$nomor). ${baris.first()}\n"
        val sisa = baris.drop(1).joinToString("\n")

        val finalText = judul + sisa

        val spannable = android.text.SpannableString(finalText)
        spannable.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            0,
            judul.length,
            android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        holder.tvDeskripsi.text = spannable
    }

    override fun getItemCount() = daftarFitur.size
}