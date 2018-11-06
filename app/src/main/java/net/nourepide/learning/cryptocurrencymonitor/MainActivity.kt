package net.nourepide.learning.cryptocurrencymonitor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import net.nourepide.learning.cryptocurrencymonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reload()

        viewModel.isLoading.observe(this, Observer {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.apply {
                    progressBar.visibility = View.GONE
                    recyclerView.animation = loadAnimation(applicationContext, R.anim.appearance)
                }
            }
        })
    }

    @MainThread
    private fun reload() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainListAdapter(this@MainActivity, viewModel).apply { notifyDataSetChanged() }
        }
    }
}
