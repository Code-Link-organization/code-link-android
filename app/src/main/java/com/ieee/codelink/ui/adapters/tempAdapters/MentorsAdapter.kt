package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardMentorBinding
import com.ieee.codelink.domain.tempModels.TempMentor


class MentorsAdapter(
    var mentors: List<TempMentor>,
    var itemClicked: (TempMentor) -> Unit
) : RecyclerView.Adapter<MentorsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardMentorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardMentorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mentors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mentors[position]

        holder.binding.root.setOnClickListener {
            itemClicked(item)
        }

        holder.binding.apply {
            tvMentorName.text = item.name
            tvPrice.text = item.price.toString()
            tvTrack.text = item.track

            setImageUsingGlide(
                view = holder.binding.ivMentorImage,
                image = item.image
            )
        }

    }


}