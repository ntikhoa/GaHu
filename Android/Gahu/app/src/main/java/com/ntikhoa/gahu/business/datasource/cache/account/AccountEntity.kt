package com.ntikhoa.gahu.business.datasource.cache.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ntikhoa.gahu.business.domain.model.Account

@Entity(tableName = "accounts")
data class AccountEntity(
    @ColumnInfo(name = "token")
    var token: String,

    @PrimaryKey
    @ColumnInfo(name = "pk")
    var pk: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "username")
    var username: String
) {
    fun toDomain(): Account {
        return Account(
            id = pk,
            token = token,
            email = email,
            username = username
        )
    }
}
