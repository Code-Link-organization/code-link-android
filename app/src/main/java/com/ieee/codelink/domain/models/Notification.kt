package com.ieee.codelink.domain.models

import android.app.Notification

data class Notification (
    var invites : MutableList<InviteRequest>
){
     fun getSize(): Int {
        return  invites.size
    }

    //todo : this method should return a mapped class of the 3 notification types (message , invitation ,join request)
    fun getNotifications(): MutableList<InviteRequest> = invites

}