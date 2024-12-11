package com.mcaps.mmm.view.customview

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class ScoreTextView : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.black)
        filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            if (source.matches(Regex("[0-9]*"))) {
                source
            } else {
                ""
            }
        })
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val score = s.toString().toIntOrNull()
        if (score != null && (score < 0 || score > 100)) {
            error = "Score value must be between 0 and 100."
        } else {
            error = null
        }
    }
}