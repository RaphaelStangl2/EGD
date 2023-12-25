package com.example.egd.data

import com.example.egd.data.entities.Invitation

data class InvitationState(
    var statusInvitationList: Array<Invitation>? = null,
    var incomingInvitationList: Array<Invitation>? = null,
    var test: Int = 0,
    var deleteInvitationList: Array<Invitation>? = null
)
