package com.example.egd.data

import com.example.egd.data.costsEnum.CostsEnum

data class CostsState(
    var costs: String = "",
    var reason: CostsEnum? = null
)
