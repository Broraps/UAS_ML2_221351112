package com.raffiargianda.uas_ml2_221351112

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatasetLink(view)
    }

    private fun setupDatasetLink(view: View) {
        val textView = view.findViewById<TextView>(R.id.tvDatasetLink)

        val fullText = "Link Dataset: Lung Disease"
        val linkText = "Lung Disease"

        val spannableString = SpannableString(fullText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val url = "https://www.kaggle.com/datasets/thedevastator/cancer-patients-and-air-pollution-a-new-link"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }

        val startIndex = fullText.indexOf(linkText)
        val endIndex = startIndex + linkText.length

        spannableString.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}