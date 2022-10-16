package com.example.transakcje.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transaction(

    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val date: Long,
    val price: Float,
    val description: String,
    val type: TransactionType,
    val category: TransactionCategory,
)
