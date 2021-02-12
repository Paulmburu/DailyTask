package com.kotdroidsicario.dailytask.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setTextData")
fun bindTextView(textView: TextView, title: String?){
    title.let { textView.setText(it) }
}