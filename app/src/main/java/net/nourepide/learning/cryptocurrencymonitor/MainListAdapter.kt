package net.nourepide.learning.cryptocurrencymonitor

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import net.nourepide.learning.cryptocurrencymonitor.MainListAdapter.MainViewHolder
import net.nourepide.learning.cryptocurrencymonitor.databinding.ItemCryptocurrencyBinding

class MainListAdapter(private val viewModel: MainViewModel) : Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemCryptocurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = viewModel.data.value!!.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
        viewHolder.binding.cryptocurrency = viewModel.data.value!![value]
        viewHolder.binding.viewModel = viewModel
    }

    class MainViewHolder(val binding: ItemCryptocurrencyBinding) : RecyclerView.ViewHolder(binding.root)
}
