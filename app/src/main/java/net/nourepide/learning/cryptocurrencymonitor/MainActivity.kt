package net.nourepide.learning.cryptocurrencymonitor

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.nourepide.learning.cryptocurrencymonitor.enity.Cryptocurrency
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter()

            data = JsonTask()
                .execute().get()
            adapter!!.notifyDataSetChanged()
        }
    }

    private inner class MainListAdapter : RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, value: Int) = MainViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.item_cryptocurrency, viewGroup, false)
        )

        override fun getItemCount() = data.size

        override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
            viewHolder.apply {
                number.text = data[value].number
                name.text = data[value].name
                symbol.text = data[value].symbol
            }
        }
    }

    private inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = itemView.findViewById<TextView>(R.id.number)!!
        val name = itemView.findViewById<TextView>(R.id.name)!!
        val symbol = itemView.findViewById<TextView>(R.id.symbol)!!
    }

    private class JsonTask : AsyncTask<Any, Unit, ArrayList<Cryptocurrency>>() {
        override fun doInBackground(vararg params: Any): ArrayList<Cryptocurrency> {
            val url = "https://api.coinmarketcap.com/v2/listings/"
            val json = getDataURL(url)

            val jsonArray = JSONObject(json).getJSONArray("data")

            val arrayLength = jsonArray.length().dec()
            val list: ArrayList<Cryptocurrency> = arrayListOf()

            for (i: Int in 0..arrayLength) {
                val jsonObject = jsonArray[i] as JSONObject

                list.add(
                    Cryptocurrency(
                        jsonObject.getString("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("symbol")
                    )
                )
            }

            return list
        }
    }
}

