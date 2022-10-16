package com.example.transakcje.ui.edit_fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.transakcje.MainActivity
import com.example.transakcje.MainViewModel
import com.example.transakcje.R
import com.example.transakcje.data.models.Transaction
import com.example.transakcje.data.models.TransactionCategory
import com.example.transakcje.data.models.TransactionType
import com.example.transakcje.databinding.EditTransactionFragmentBinding
import com.example.transakcje.ui.date_picker.TransactionDatePicker
import java.text.SimpleDateFormat
import java.util.*

class EditTransactionFragment : Fragment() {

    private val viewModel by viewModels<EditTransactionViewModel>()
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: EditTransactionFragmentBinding? = null
    private val binding get() = _binding!!

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (requireActivity() as MainActivity).setBottomNavVisibility(true)
            isEnabled = false
            requireActivity().onBackPressed()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = EditTransactionFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
        setTransactionData(mainVm.getSelectedTransaction()!!)
        setupOnClicks()
        setupFocusListeners()
        setupTextChangedListeners()
        setupToolbar()
        binding.titleTv.append(mainVm.getSelectedTransaction()!!.category.name)

    }

    private fun setupToolbar() {
        binding.arrowBackImg.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupTextChangedListeners() {

        binding.amountEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.amountEt.setBackgroundResource(R.drawable.text_view_outline_focused)
                binding.errorHintTv.visibility = View.INVISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    p0.toString().toFloat()
                }catch (e: Exception){
                    binding.amountEt.setBackgroundResource(R.drawable.text_view_outline_error)
                    binding.errorHintTv.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setupFocusListeners() {
        binding.amountEt.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                binding.amountEt.setBackgroundResource(R.drawable.text_view_outline)
            }
        }
    }

    private fun setupOnClicks() {

        binding.calendarImg.setOnClickListener {
            showDatePickerDialog()
        }

        binding.deleteBtn.setOnClickListener {

            mainVm.getSelectedTransaction()?.let { trans ->
                mainVm.deleteTransactions(listOf(trans))
                mainVm.unselectTransaction()
            }
            requireActivity().onBackPressed()
        }

        binding.saveBtn.setOnClickListener {
            updateTransaction()
        }
    }

    private fun updateTransaction() {
        val updateTrans = createTransaction()
        mainVm.updateTransactions(updateTrans)
        requireActivity().onBackPressed()
    }

    private fun createTransaction(): Transaction {
        val type = when(binding.radioGroup.checkedRadioButtonId){
            binding.incomeRb.id -> TransactionType.INCOME
            else -> TransactionType.OUTCOME
        }

        val category = when(binding.appCompatSpinner.selectedItem.toString()){
            "FOOD" -> TransactionCategory.FOOD
            "OTHERS" -> TransactionCategory.OTHERS
            "HOUSEHOLD" -> TransactionCategory.HOUSEHOLD
            "TRANSPORTATION " -> TransactionCategory.TRANSPORTATION
            else -> TransactionCategory.OTHERS

        }

        val amount = binding.amountEt.text.toString()
        val desc = binding.descEt.text.toString()

        return Transaction(mainVm.getSelectedTransaction()!!.uid, viewModel.date, amount.toFloat(), desc, type, category)
    }

    private fun setTransactionData(transaction: Transaction) {
        setCurrentAmount(transaction.price)
        setCurrentType(transaction.type)
        setCurrentCategory(transaction.category)
        setCurrentDate(transaction.date)
        setCurrentDescription(transaction.description)
    }

    private fun showDatePickerDialog() {
        val newDatePicker = TransactionDatePicker {day, month, year ->

            val dayPlaceholder = if(day < 10) "0$day" else "$day"
            binding.dayTv.text = dayPlaceholder

            val monthPlaceholder = if(month + 1 < 10) "0${month + 1}" else "${month + 1}"
            binding.monthTv.text = monthPlaceholder
            binding.yearTv.text = year.toString()


            val date = Calendar.getInstance()
            date.set(year, month, day)
            viewModel.date = date.timeInMillis
        }

        newDatePicker.show(parentFragmentManager, "DatePicker")
    }

    private fun setCurrentDescription(description: String) {

        binding.descEt.setText(description)

    }

    private fun setCurrentDate(date: Long) {
        viewModel.date = date
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val datePlaceholder = sdf.format(date)
        val list = datePlaceholder.split("-")

        binding.dayTv.text = list[0]
        binding.monthTv.text = list[1]
        binding.yearTv.text = list [2]
    }

    private fun setCurrentCategory(category: TransactionCategory) {
        val adapter = ArrayAdapter(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            TransactionCategory.values().map { enum -> enum.name}
        )
        binding.appCompatSpinner.adapter = adapter
        val position = adapter.getPosition(category.name)
        binding.appCompatSpinner.setSelection(position)
    }

    private fun setCurrentType(type: TransactionType) {
        val checkId = when(type){
            TransactionType.INCOME -> binding.incomeRb.id
            TransactionType.OUTCOME -> binding.outcomeRb.id
        }

        binding.radioGroup.check(checkId)
    }

    private fun setCurrentAmount(price: Float) {
        binding.amountEt.setText(price.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun handleOnBackPressed() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
}