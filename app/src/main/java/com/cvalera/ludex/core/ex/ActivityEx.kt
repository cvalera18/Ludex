package com.cvalera.ludex.core.ex

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cvalera.ludex.R

fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Activity.span(
    unselectedPart: String,
    selectedPart: String
): SpannableStringBuilder {
    val context: Context = this
    val completedText = SpannableStringBuilder("$unselectedPart $selectedPart")

    completedText.apply {
        setSpan(
            StyleSpan(Typeface.BOLD),
            unselectedPart.length,
            (unselectedPart + selectedPart).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    completedText.apply {
        setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.seed)),
            unselectedPart.length,
            (unselectedPart + selectedPart).length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return completedText
}