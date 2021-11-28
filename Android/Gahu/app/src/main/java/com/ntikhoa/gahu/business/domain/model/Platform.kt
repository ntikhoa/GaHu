package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.game.PlatformEntity

data class Platform(
    var id: String,
    var name: String
) {
    fun toEntity() =
        PlatformEntity(
            pk = id,
            name = name
        )
}