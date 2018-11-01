package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import net.nourepide.learning.cryptocurrencymonitor.databinding.ActivityMainBinding
import net.nourepide.learning.cryptocurrencymonitor.entity.Cryptocurrency
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val contentView by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView.list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter(viewModel)
        }

        thread {
            viewModel.initialization()

            runOnUiThread { contentView.reload() }
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

    private fun ActivityMainBinding.reload() {
        progressBar.visibility = View.GONE
        list.adapter!!.notifyDataSetChanged()
        list.startAnimation(AnimationUtils.loadAnimation(this.root.context, R.anim.appearance))
    }
}
