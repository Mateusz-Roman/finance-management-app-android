package com.example.transakcje.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transakcje.R
import com.example.transakcje.data.models.Transaction
import com.example.transakcje.data.models.TransactionType
import com.example.transakcje.databinding.TransactionRowBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionsAdapter(
    private val transactions: List<Transaction>,
    private val onClick: (Transaction, Int) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(binding: TransactionRowBinding):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                onClick(transactions[adapterPosition], adapterPosition)
            }
        }
            val date = binding.dateTv
            val price = binding.priceTv
            val category = binding.categoryTv
            val type = binding.typeTv
            val icon = binding.imageView
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            TransactionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        bindData(holder, position)
    }

    private fun bindData(holder: TransactionViewHolder, position: Int){

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pl_"))
        val date = Date(transactions[position].date)
        val datePlaceholder = sdf.format(date)

        val typeIconResource = when(transactions[position].type){
            TransactionType.INCOME -> R.drawable.ic_wallet_add
            TransactionType.OUTCOME -> R.drawable.ic_wallet__remove
        }

        holder.price.text = transactions[position].price.toString()
        holder.category.text = transactions[position].category.name
        holder.type.text = transactions[position].type.name
        holder.date.text = datePlaceholder
         when(transactions[position].type){
            TransactionType.INCOME -> holder.price.text = "+${transactions[position].price}zł"
            TransactionType.OUTCOME -> holder.price.text = "-${transactions[position].price}zł"
        }


        holder.icon.setImageResource(typeIconResource)
    }

    override fun getItemCount(): Int {
       return transactions.size
    }
}