package com.ntikhoa.gahu.presentation.trophy

import com.ntikhoa.gahu.business.domain.model.Trophy

data class TrophyState(
    var isLoading: Boolean = false,
    var trophy: Trophy? = null,
    var message: String? = null
)