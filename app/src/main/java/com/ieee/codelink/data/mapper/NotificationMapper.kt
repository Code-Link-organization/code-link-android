package com.ieee.codelink.data.mapper

import com.ieee.codelink.domain.models.InviteRequest
import com.ieee.codelink.domain.models.Notification

fun invitationsToNotification(invitations : MutableList<InviteRequest>) = Notification(invites = invitations)
