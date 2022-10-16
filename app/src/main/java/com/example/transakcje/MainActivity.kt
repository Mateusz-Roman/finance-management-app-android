package com.example.transakcje

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.transakcje.data.models.Transaction
import com.example.transakcje.data.models.TransactionCategory
import com.example.transakcje.data.models.TransactionType
import com.example.transakcje.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mainVm by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavVisibility(mainVm.isBottomVisible)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.addTransactionFb.setOnClickListener{
            setBottomNavVisibility(false)
            navController.navigate(R.id.addTransactionFragment)
        }

        // mainVm.insertTransactions(createTransaction())
    }

    fun setBottomNavVisibility(bool: Boolean){

        mainVm.isBottomVisible = bool

        val isVisible = when(bool){
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }

        binding.cardView.visibility = isVisible
        binding.addTransactionFb.visibility = isVisible

    }

    private fun createTransaction() =
        Transaction(
            0,
            1L,
            15F,
            "Opis",
            TransactionType.INCOME,
            TransactionCategory.HOUSEHOLD)
}