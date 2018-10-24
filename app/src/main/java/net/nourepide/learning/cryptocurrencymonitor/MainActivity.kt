package net.nourepide.learning.cryptocurrencymonitor

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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

            JsonTask().execute(this)
        }
    }

    private class MainListAdapter : RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, value: Int) = MainViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.item_cryptocurrency, viewGroup, false)
        )

        override fun getItemCount() = data.size

        override fun onBindViewHolder(viewHolder: MainViewHolder, value: Int) {
            viewHolder.apply {
                val source = data[value]

                number.text = source.number
                name.text = source.name
                symbol.text = source.symbol
            }
        }
    }

    private class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number = itemView.findViewById<TextView>(R.id.number)!!
        val name = itemView.findViewById<TextView>(R.id.name)!!
        val symbol = itemView.findViewById<TextView>(R.id.symbol)!!
    }

    private data class ResultTask(
        val cryptocurrencies: ArrayList<Cryptocurrency>,
        val recyclerView: RecyclerView
    )

    private class JsonTask : AsyncTask<RecyclerView, Unit, ResultTask>() {
        override fun doInBackground(vararg params: RecyclerView): ResultTask {
            val url = "https://api.coinmarketcap.com/v2/listings/"
            val json = getDataURL(url)

            val jsonArray = JSONObject(json).getJSONArray("data")
            val list = arrayListOf<Cryptocurrency>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray[i] as JSONObject

                list.add(
                    Cryptocurrency(
                        jsonObject.getString("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("symbol")
                    )
                )
            }

            return ResultTask(list, params.first())
        }

        override fun onPostExecute(result: ResultTask) {
            val (cryptocurrencies, recyclerView) = result

            data = cryptocurrencies
            recyclerView.adapter!!.notifyDataSetChanged()

            val animation = AnimationUtils.loadAnimation(recyclerView.context, R.anim.appearance)
            recyclerView.animation = animation
        }
    }
}

