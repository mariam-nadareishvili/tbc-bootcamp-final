package com.example.android_bootcamp.presentation.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackbar(message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .into(this)
}