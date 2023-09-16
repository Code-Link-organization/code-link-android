package com.ieee.codelink.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ieee.codelink.R
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.getTimeDifference
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.common.setImageUsingGlideCenterCrop
import com.ieee.codelink.databinding.ItemNotificationBinding
import com.ieee.codelink.domain.models.Notification


class NotificationsAdapter(
    var notifications: MutableList<Notification>,
    private val openProfile: (Notification) -> Unit,
    private val acceptAction: (Notification) -> Unit,
    private val rejectAction: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = notifications.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        setViews(holder, notification)
        setOnClicks(holder, notification)
    }

    private fun setViews(holder: NotificationsAdapter.ViewHolder, notification: Notification) {
        holder.binding.apply {
            val context = root.context
            val img =
                if (notification.team != null) notification.team!!.imageUrl else notification.user?.imageUrl
            val errorImage =
                if (notification.team != null) R.drawable.teamwork else R.drawable.ic_profile
            val msg = if (notification.team != null) getTeamInvitationMessage(
                notification,
                context
            ) else getUserInvitationMessage(notification, context)
            tvNotificationMessage.text = msg
            textView2.text = getTimeDifference(notification.created_at)
            setImageUsingGlideCenterCrop(
                view = holder.binding.ivUserImage,
                image = getImageForGlide(img),
                errorImage = errorImage
            )
        }
    }


    private fun setOnClicks(holder: ViewHolder, notification: Notification) {
        holder.binding.ivUserImage.setOnClickListener {
            openProfile(notification)
        }
        holder.binding.btnAccept.setOnClickListener {
            acceptAction(notification)
        }
        holder.binding.btnReject.setOnClickListener {
            rejectAction(notification)
        }
    }

    fun removeNotification(notificationId: Int) {
        val index = notifications.indexOfFirst { it.id == notificationId }
        notifications.removeAt(index)
        notifyItemRemoved(index)
    }

    fun isEmpty() = notifications.isEmpty()

    private fun getTeamInvitationMessage(notification: Notification, context: Context): String {
        val invitaionString = context.getString(R.string.team_invitaion_message)
        return invitaionString +" ${notification.team!!.name}"
    }

    private fun getUserInvitationMessage(notification: Notification, context: Context): String? {
        val invitaionString = context.getString(R.string.user_invitaion_message)
        notification.user?.let{
         return it.name + " $invitaionString "+ (notification.team?.name ?: " ")
        }
        return null
    }


}

