package com.mcaps.mmm.view.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class EmailTextView : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.black)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            setError("Email tidak boleh kosong", null)
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            setError("Email tidak valid", null)
        } else {
            error = null
        }
    }
}