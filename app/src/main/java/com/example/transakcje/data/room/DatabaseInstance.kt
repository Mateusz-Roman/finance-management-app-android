package com.example.transakcje.data.room

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    private var instance: TransactionsDatabase? = null

    fun getInstance(context: Context): TransactionsDatabase {
        if (instance == null){
            synchronized(TransactionsDatabase::class.java){
                instance = roomBuild(context)
            }
        }

        return instance!!
    }

    private fun roomBuild(context: Context): TransactionsDatabase =
        Room.databaseBuilder(context, TransactionsDatabase::class.java, "transactions_database")
            .fallbackToDestructiveMigration()
            .build()
}