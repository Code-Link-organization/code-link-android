package com.ieee.codelink.common.extension

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun AlertDialog.showSpacedDialog(width: Int) {
    this.show()
    val attrs = this.window?.attributes
    attrs?.width = width - (width / 5)
    this.window?.attributes = attrs
}

fun <VB : ViewBinding> Fragment.createDialog(
    inflateMethod: (LayoutInflater) -> VB,
    bindMethod: AlertDialog.(VB) -> Unit,
) {
    val dialog = AlertDialog.Builder(requireContext()).create()
    val binding = inflateMethod.invoke(layoutInflater)
    bindMethod.invoke(dialog, binding)
    dialog.apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(binding.root)

    }.showSpacedDialog(width)
}