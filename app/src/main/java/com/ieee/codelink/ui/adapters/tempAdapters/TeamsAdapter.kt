package com.ieee.codelink.ui.adapters.tempAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ieee.codelink.R
import com.ieee.codelink.databinding.CardTeamBinding
import com.ieee.codelink.domain.tempModels.TempTeam


class TeamsAdapter(
    var teams: MutableList<TempTeam>,
    private val joinTeam: (TempTeam) -> Unit,
    private val openTeam: (TempTeam) -> Unit,
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

    private fun setViews(holder: ViewHolder, team: TempTeam) {
        holder.binding.apply {
            tvTeamName.text = team.name
            Glide.with(holder.binding.ivTeamImage)
                .load(team.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(holder.binding.ivTeamImage)

        }
    }

    private fun setOnClicks(holder: ViewHolder, team: TempTeam) {
        holder.binding.root.setOnClickListener {
            openTeam(team)
        }
        holder.binding.btnJoin.setOnClickListener {
            joinTeam(team)
        }

    }


}