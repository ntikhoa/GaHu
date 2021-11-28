package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ntikhoa.gahu.business.domain.model.Platform

@Entity(tableName = "platforms")
data class PlatformEntity(
    @PrimaryKey
    @ColumnInfo(name = "pk")
    var pk: String,

    @ColumnInfo(name = "name")
    var name: String
) {
    fun toDomain() =
        Platform(
            id = pk,
            name = name
        )
}