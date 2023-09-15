package com.ieee.codelink.domain.models.responseData

import com.ieee.codelink.domain.models.InviteRequest

data class InvitesData(
    var invite_requests: List<InviteRequest>
)