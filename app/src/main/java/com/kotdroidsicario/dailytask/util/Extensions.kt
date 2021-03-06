package com.kotdroidsicario.dailytask.util

import android.app.Activity
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar


fun Int.validateTime() : String = "%02d".format(this)

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

inline fun <reified T> Activity.navigateTo(noinline intentExtras: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    intentExtras?.run {
        intentExtras(intent)
    }
    startActivity(intent)
}