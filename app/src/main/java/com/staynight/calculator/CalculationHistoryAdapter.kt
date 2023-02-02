package com.staynight.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.staynight.calculator.databinding.ItemCalculationHistoryBinding

class CalculationHistoryAdapter(
    private val scrollToBottom: () -> Unit
) :
    ListAdapter<String, CalculationHistoryAdapter.CalculationHistoryViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }) {

    fun addCalculationToHistory(calculation: String) {
        val newList = listOf(calculation) + currentList
        submitList(newList.toMutableList()) {
            scrollToBottom.invoke()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalculationHistoryViewHolder =
        CalculationHistoryViewHolder(
            ItemCalculationHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CalculationHistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CalculationHistoryViewHolder(private val item: ItemCalculationHistoryBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(calculation: String) {
            item.tvCalculation.text = calculation
        }
    }
}