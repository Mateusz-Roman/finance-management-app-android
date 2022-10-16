package com.example.transakcje.ui.transactions_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transakcje.MainActivity
import com.example.transakcje.MainViewModel
import com.example.transakcje.R
import com.example.transakcje.databinding.TransactionsFragmentBinding
import com.example.transakcje.ui.adapters.TransactionsAdapter

class TransactionsFragment : Fragment() {


    private val viewModel by viewModels<TransactionsViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: TransactionsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TransactionsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainVm.getAllTransactions().observe(viewLifecycleOwner) {transactions ->
            binding.recyclerView.adapter = TransactionsAdapter(transactions) { transaction,position ->
                mainVm.selectTransaction(transaction)
                (requireActivity() as MainActivity).setBottomNavVisibility(false)
                findNavController().navigate(R.id.editTransactionFragment)
            }
        }
    }


}