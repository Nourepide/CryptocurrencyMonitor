package net.nourepide.learning.cryptocurrencymonitor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, value: Int) = MainViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cryptocurrency, viewGroup, false)
    )

    override fun getItemCount() = viewModel.data.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
        val source = viewModel.data[value]

        viewHolder.apply {
            number.text = source.number
            name.text = source.name
            symbol.text = source.symbol
        }
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = itemView.findViewById<TextView>(R.id.number)!!
        val name = itemView.findViewById<TextView>(R.id.name)!!
        val symbol = itemView.findViewById<TextView>(R.id.symbol)!!
    }
}
