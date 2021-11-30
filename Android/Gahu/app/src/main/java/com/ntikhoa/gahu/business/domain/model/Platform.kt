package com.ntikhoa.gahu.business.domain.model

import androidx.recyclerview.widget.DiffUtil
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

    companion object {
        val ALL_PLATFORM = Platform("-1", "All")
    }
}