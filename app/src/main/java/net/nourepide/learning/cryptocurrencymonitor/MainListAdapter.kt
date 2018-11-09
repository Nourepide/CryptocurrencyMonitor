package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import net.nourepide.learning.cryptocurrencymonitor.MainListAdapter.MainViewHolder
import net.nourepide.learning.cryptocurrencymonitor.databinding.ItemCryptocurrencyBinding

class MainListAdapter(lifecycle: LifecycleOwner, private val viewModel: MainViewModel) : Adapter<MainViewHolder>() {

    init {
        viewModel.data.observe(lifecycle, Observer { notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCryptocurrencyBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = viewModel.data.value?.size ?: 0

    override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
        viewHolder.binding.cryptocurrency = viewModel.data.value!![value]
    }

    class MainViewHolder(val binding: ItemCryptocurrencyBinding) : RecyclerView.ViewHolder(binding.root)
}
