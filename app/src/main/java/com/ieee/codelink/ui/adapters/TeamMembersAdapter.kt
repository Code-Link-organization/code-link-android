package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.setImageUsingGlideCenterCrop
import com.ieee.codelink.databinding.ItemTeamMemberBinding
import com.ieee.codelink.domain.models.User


class TeamMembersAdapter(
    var items: List<User>,
    var itemClicked : (User) -> Unit
) : RecyclerView.Adapter<TeamMembersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTeamMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTeamMemberBinding.inflate(
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
            setImageUsingGlideCenterCrop(
                view = ivUserImage,
                image = getImageForGlide(item.imageUrl)
            )
            tvUserName.text = item.name
            tvTrack.text = item.track
        }

    }


}