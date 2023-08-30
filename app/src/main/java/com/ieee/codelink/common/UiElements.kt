package com.ieee.codelink.common

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ieee.codelink.R

fun showSnackbar(message: String, context: Context, view: View) {

    val snackbar = Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_SHORT
    )
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.color_text))
    snackbar.setTextColor(ContextCompat.getColor(context, R.color.color_text))
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.color_secondary_background))
    snackbar.show()
}

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showDialog(
    context: Context,
    title: String,
    message: String,
    positiveClicked: () -> Unit,
    negativeClicked: () -> Unit = {},
    setOnDismiss : () -> Unit = {}
) {
    val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("Yes") { dialog, _ ->
        positiveClicked()
        dialog.dismiss()
    }
    builder.setNegativeButton("No") { dialog, _ ->
        negativeClicked()
        dialog.dismiss()
    }
    builder.setOnDismissListener {
        setOnDismiss()
    }
    builder.show().apply {
        val positiveButton = getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setBackgroundResource(R.drawable.custom_button_ripple)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            positiveButton.setTextColor(context.resources.getColor(R.color.color_text))
        }

        val negativeButton = getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton.setBackgroundResource(R.drawable.custom_button_ripple)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            negativeButton.setTextColor(context.resources.getColor(R.color.color_text))
        }
    }



}