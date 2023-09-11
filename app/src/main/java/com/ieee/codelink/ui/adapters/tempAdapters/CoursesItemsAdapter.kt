package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardCourseBinding
import com.ieee.codelink.domain.tempModels.TempUserSearch


class CoursesItemsAdapter(
    var items: List<TempUserSearch>,
    var itemClicked : (TempUserSearch) -> Unit
) : RecyclerView.Adapter<CoursesItemsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardCourseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.root.setOnClickListener {
            itemClicked(item)
        }

        holder.binding.apply {
            setImageUsingGlide(
                view = itemImage,
                image = item.image
            )
            itemName.text = item.name
        }

    }


}