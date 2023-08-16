package com.ieee.codelink.common.extension

import android.widget.EditText
import androidx.core.widget.doOnTextChanged

inline fun EditText.doOnTextChangedIfNotBlankOrNull(crossinline action: (text: String) -> Unit) {
    this.doOnTextChanged { text, _, _, _ ->
        text?.let {
            val str = it.toString()
            if (str.isNotBlank()) action(str)
        }
    }
}

fun EditText.setTextWithCursorPosition(text: String) {
    this.setText(text)
    this.setSelection(text.length)
}

