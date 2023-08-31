package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.databinding.CardTrackBinding
import com.ieee.codelink.domain.models.TempModel


class SearchItemsAdapter(
    var items: ArrayList<TempModel> ,
    var itemClicked : (TempModel) -> Unit
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
            itemImage.setImageResource(item.img)
            itemName.text = item.title
        }

    }


}