package com.example.egd.data

import com.example.egd.data.costsEnum.CostsEnum

data class CostsState(
    var costs: String = "",
    var reason: CostsEnum? = null,
    var showCosts: Boolean = false,
    var triedToSubmit: Boolean = false,
)
