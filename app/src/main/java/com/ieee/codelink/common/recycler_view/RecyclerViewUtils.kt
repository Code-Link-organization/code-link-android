package com.ieee.codelink.common.recycler_view

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun Fragment.getItemDecoration(
    @DrawableRes resourceId: Int,
    orientation: Int = RecyclerView.VERTICAL,
): DividerItemDecoration {
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    ContextCompat.getDrawable(requireContext(), resourceId)?.let {
        dividerItemDecoration.setDrawable(it)
    }
    return dividerItemDecoration
}