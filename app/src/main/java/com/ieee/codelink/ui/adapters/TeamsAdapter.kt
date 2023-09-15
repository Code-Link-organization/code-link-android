package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardTeamBinding
import com.ieee.codelink.domain.models.Team


class TeamsAdapter(
    var teams: MutableList<Team>,
    private val joinTeam: (Team) -> Unit,
    private val openTeam: (Team) -> Unit,
) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardTeamBinding.inflate(
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

            setImageUsingGlide(
                view = holder.binding.ivTeamImage,
                image = getImageForGlide(team.imageUrl),
                errorImage = R.drawable.teamwork
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, team: Team) {
        holder.binding.root.setOnClickListener {
            openTeam(team)
        }
        holder.binding.btnJoin.setOnClickListener {
            joinTeam(team)
        }

    }


}