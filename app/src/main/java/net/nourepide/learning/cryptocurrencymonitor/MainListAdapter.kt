package net.nourepide.learning.cryptocurrencymonitor

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import net.nourepide.learning.cryptocurrencymonitor.MainListAdapter.MainViewHolder
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency

class MainListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<MainViewHolder>() {
    class MainViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Cryptocurrency) {
            binding.setVariable(BR.cryptocurrency, data)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, value: Int) = MainViewHolder(getLayout(viewGroup))

    override fun getItemCount() = viewModel.data.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
        viewHolder.bind(viewModel.data[value])
    }

    private fun getLayout(viewGroup: ViewGroup): ViewDataBinding = DataBindingUtil
        .inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_cryptocurrency,
            viewGroup,
            false
        )
}
