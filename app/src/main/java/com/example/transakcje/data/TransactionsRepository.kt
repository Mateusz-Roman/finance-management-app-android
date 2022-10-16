package com.example.transakcje.data

import android.app.Application
import com.example.transakcje.data.models.Transaction
import com.example.transakcje.data.room.DatabaseInstance

class TransactionsRepository (app: Application){

    private val transactionsDao = DatabaseInstance.getInstance(app).transactionsDao()

    suspend fun insertTransaction(transaction: Transaction){
        transactionsDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction){
        transactionsDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: List<Transaction>){
        transactionsDao.deleteTransactions(transaction)
    }

    fun getAllTransactions() = transactionsDao.getAllTransactions()
    fun getAllIncomes() = transactionsDao.getAllIncomes()
    fun getAllOutcomes() = transactionsDao.getAllOutcomes()
    fun getSumOfIncomeByCategory() = transactionsDao.getSumOfIncomesGroupByCategory()
    fun getSumOfOutcomeByCategory() = transactionsDao.getSumOfOutcomesGroupByCategory()

}