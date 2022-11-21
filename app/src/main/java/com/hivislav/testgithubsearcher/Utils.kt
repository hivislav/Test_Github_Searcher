package com.hivislav.testgithubsearcher

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun TextView.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}