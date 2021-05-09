package com.example.finmag

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Entry (
        @PrimaryKey(autoGenerate = true) val uid: Int,
        val type: Int,
        val category: String,
        val amount: BigDecimal,
        val place: String,
        val date: Date?
        )