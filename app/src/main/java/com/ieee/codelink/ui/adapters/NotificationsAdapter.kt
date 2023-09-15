package com.ieee.codelink.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import com.ieee.codelink.common.getCurrentUtcDateTime
import com.ieee.codelink.common.getImageForGlide
import com.ieee.codelink.common.getTimeDifference
import com.ieee.codelink.common.setImageUsingGlide
import com.ieee.codelink.databinding.CardLikePersonBinding
import com.ieee.codelink.databinding.ItemNotificationBinding
import com.ieee.codelink.domain.models.InviteRequest
import com.ieee.codelink.domain.models.Notification
import com.ieee.codelink.domain.models.responseData.LikeData


class NotificationsAdapter(
    var notifications: MutableList<InviteRequest>,
    private val openProfile: (Int) -> Unit,
    private val acceptAction: (Int) -> Unit,
    private val rejectAction: (Int) -> Unit
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

    private fun setViews(holder: NotificationsAdapter.ViewHolder, notification: InviteRequest) {
        holder.binding.apply {
            tvNotificationMessage.text = notification.status
            textView2.text = getTimeDifference(notification.created_at)
            setImageUsingGlide(
                view = holder.binding.ivUserImage,
                image = getImageForGlide()
            )
        }
    }

    private fun setOnClicks(holder: ViewHolder, notification: InviteRequest) {
        holder.binding.ivUserImage.setOnClickListener {
            openProfile(notification.user_id)
        }
        holder.binding.btnAccept.setOnClickListener {
            acceptAction(notification.id)
        }
        holder.binding.btnReject.setOnClickListener {
            rejectAction(notification.id)
        }
    }

    fun removeNotification(notificationId: Int) {
        val index = notifications.indexOfFirst { it.id == notificationId }
        notifications.removeAt(index)
        notifyItemRemoved(index)
    }

    fun isEmpty() = notifications.isEmpty()


}

