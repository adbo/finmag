package com.example.finmag

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry")
    fun getAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE uid = :id")
    fun getEntry(id: Int): Flow<Entry>

    @Query("SELECT IFNULL(SUM(amount),0) FROM entry WHERE type = 1")
    fun getIncome(): Flow<Float>

    @Query("SELECT IFNULL(SUM(amount),0) FROM entry WHERE type = 0")
    fun getExpenses(): Flow<Float>

    @Update
    suspend fun update(item: Entry)

    @Insert
    suspend fun insert(item: Entry)

    @Delete
    suspend fun delete(item: Entry)
}