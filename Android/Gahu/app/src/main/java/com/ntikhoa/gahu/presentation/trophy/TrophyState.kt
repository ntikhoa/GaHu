package com.ntikhoa.gahu.presentation.trophy

import com.ntikhoa.gahu.business.domain.model.TrophyProfile

data class TrophyState(
    var isLoading: Boolean = false,
    var trophyProfile: TrophyProfile? = null,
    var message: String? = null
)