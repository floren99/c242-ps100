package com.mcaps.mmm.view.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class PassTextView : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.black)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isEmpty()) {
            setError("Password tidak boleh kosong", null)
        } else if (s.toString().length < 8) {
            setError("Password tidak boleh kurang dari 8 karakter", null)
        } else if (!s.any { it.isDigit() }) {
            setError("Password minimal mengandung 1 angka", null)
        } else {
            error = null
        }
    }
}