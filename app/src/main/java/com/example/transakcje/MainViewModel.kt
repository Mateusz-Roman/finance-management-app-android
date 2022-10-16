package com.example.transakcje

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.transakcje.data.TransactionsRepository
import com.example.transakcje.data.models.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application): AndroidViewModel(app) {

    var isBottomVisible = true

    private var selectedTransaction: Transaction? = null

    private val repo = TransactionsRepository(app)

    fun insertTransactions(transaction: Transaction) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.insertTransaction(transaction)
        }

    fun updateTransactions(transaction: Transaction) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.updateTransaction(transaction)
        }

    fun deleteTransactions(transaction: List<Transaction>) =
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteTransaction(transaction)
        }

    fun getAllTransactions() = repo.getAllTransactions().asLiveData(viewModelScope.coroutineContext)
    fun getAllIncomes() = repo.getAllIncomes().asLiveData(viewModelScope.coroutineContext)
    fun getAllOutcomes() = repo.getAllOutcomes().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfIncomeByCategory() = repo.getSumOfIncomeByCategory().asLiveData(viewModelScope.coroutineContext)
    fun getSumOfOutcomeByCategory() = repo.getSumOfOutcomeByCategory().asLiveData(viewModelScope.coroutineContext)

    fun selectTransaction(transaction: Transaction) {
        selectedTransaction = transaction
    }

    fun unselectTransaction() {
        selectedTransaction = null
    }

    fun getSelectedTransaction() = selectedTransaction
}