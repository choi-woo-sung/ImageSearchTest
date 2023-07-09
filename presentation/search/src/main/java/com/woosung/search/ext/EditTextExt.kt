package com.woosung.search.ext

import android.widget.EditText

fun EditText.setTextIfNewWithSelection(text: CharSequence) {
    val oldText = this.text?.toString() ?: ""
    if (text.toString() != oldText) {
        setText(text)
        text.lastIndex.takeIf { it > 0 }?.let(::setSelection)
    }
}
