package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import net.nourepide.learning.cryptocurrencymonitor.enity.Cryptocurrency
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter(getViewModel())

            thread {
                initialization(getViewModel())

                runOnUiThread { reload() }
            }
        }
    }

    private fun initialization(viewModel: MainViewModel) {
        when (viewModel.isInitialized) {
            false -> viewModel.isInitialized = true
            true -> return
        }

        val jsonArray = JSONObject(Utils.getDataURL()).getJSONArray("data")

        (0 until jsonArray.length())
            .map { jsonArray[it] as JSONObject }
            .forEach {
                viewModel.data += Cryptocurrency(
                    it.getString("id"),
                    it.getString("name"),
                    it.getString("symbol")
                )
            }
    }

    private fun RecyclerView.reload() {
        adapter!!.notifyDataSetChanged()
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.appearance))
    }

    private fun getViewModel() = ViewModelProviders
        .of(this)
        .get(MainViewModel::class.java)
}
