package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.data.remote.BASE_URL_FOR_IMAGE
import com.ieee.codelink.databinding.CardTrackBinding
import com.ieee.codelink.domain.tempModels.TempSearchItem


class SearchItemsAdapter(
    var items: ArrayList<TempSearchItem>,
    var itemClicked : (TempSearchItem) -> Unit
) : RecyclerView.Adapter<SearchItemsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardTrackBinding.inflate(
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

        holder.binding.apply{

            setImageUsingGlide(
                view = itemImage,
                image = item.img
            )

            itemName.text = item.title
        }

    }


}