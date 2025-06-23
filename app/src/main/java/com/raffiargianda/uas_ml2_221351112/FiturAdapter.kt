package com.raffiargianda.uas_ml2_221351112

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FiturAdapter(private val daftarFitur: List<Fitur>) :
    RecyclerView.Adapter<FiturAdapter.FiturViewHolder>() {

    class FiturViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDeskripsi: TextView = view.findViewById(R.id.tvFiturDeskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiturViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fitur, parent, false)
        return FiturViewHolder(view)
    }

    override fun onBindViewHolder(holder: FiturViewHolder, position: Int) {
        val fitur = daftarFitur[position]
        val nomor = position + 1

        val teksLengkap = "<b>$nomor). ${fitur.deskripsi}</b>"
        holder.tvDeskripsi.text = Html.fromHtml(teksLengkap, Html.FROM_HTML_MODE_LEGACY)
    }

    override fun getItemCount() = daftarFitur.size
}