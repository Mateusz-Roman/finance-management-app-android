package com.example.transakcje.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transakcje.data.models.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionsDatabase :RoomDatabase() {
    abstract fun transactionsDao() : TransactionsDao
}