package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import net.nourepide.learning.cryptocurrencymonitor.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter(viewModel)
        }

        thread {
            viewModel.initialization()

            runOnUiThread { reload() }
        }
    }

    private fun reload() {
        binding.apply {
            progressBar.visibility = View.GONE
            list.adapter!!.notifyDataSetChanged()
            list.startAnimation(AnimationUtils.loadAnimation(this.root.context, R.anim.appearance))
        }
    }
}
