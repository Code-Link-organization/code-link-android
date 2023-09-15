package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlideCenterCrop
import com.ieee.codelink.databinding.CardInviteTeamBinding
import com.ieee.codelink.domain.models.Team


class InviteUserAdapter(
    var teams: MutableList<Team>,
) : RecyclerView.Adapter<InviteUserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardInviteTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardInviteTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = teams.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams[position]
        setViews(holder, team)
        setOnClicks(holder, team)
    }

    private fun setViews(holder: ViewHolder, team: Team) {
        holder.binding.apply {
            tvTeamName.text = team.name
            setImageUsingGlideCenterCrop(
                view = holder.binding.ivTeamImage,
                image = getImageForGlide(team.imageUrl)
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, team: Team) {
        holder.binding.apply {
            root.setOnClickListener {
                checkBox.toggle()
                team.toggleSelected()
            }
        }
    }

    fun getCheckedTeams(): List<Team> = teams.filter { it.isSelected }

}