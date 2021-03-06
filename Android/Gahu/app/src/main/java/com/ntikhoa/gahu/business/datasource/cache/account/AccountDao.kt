package com.ntikhoa.gahu.business.datasource.cache.account

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE email = :email")
    suspend fun searchByEmail(email: String): AccountEntity?

    @Query("SELECT * FROM accounts WHERE token = :token")
    suspend fun getAccount(token: String): AccountEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplace(account: AccountEntity): Long

    @Query("DELETE FROM accounts WHERE email = :email")
    suspend fun deleteByEmail(email: String)
}