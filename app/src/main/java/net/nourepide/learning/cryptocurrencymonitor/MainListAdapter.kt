package net.nourepide.learning.cryptocurrencymonitor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.nourepide.learning.cryptocurrencymonitor.MainListAdapter.MainViewHolder
import net.nourepide.learning.cryptocurrencymonitor.databinding.ItemCryptocurrencyBinding

class MainListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemCryptocurrencyBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    )

    override fun getItemCount() = viewModel.data.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
        viewHolder.binding.cryptocurrency = viewModel.data[value]
    }

    class MainViewHolder(val binding: ItemCryptocurrencyBinding) : RecyclerView.ViewHolder(binding.root)
}
